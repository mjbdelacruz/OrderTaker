package com.lalafood.controller;

import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lalafood.domain.dto.ErrorDTO;
import com.lalafood.domain.dto.OrderDTO;
import com.lalafood.domain.dto.PatchOrderDTO;
import com.lalafood.domain.dto.PlaceOrderDTO;
import com.lalafood.domain.entity.Order;
import com.lalafood.repository.OrderRepository;
import com.lalafood.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/orders", consumes = "application/json", produces = "application/json")
	public ResponseEntity<OrderDTO> placeOrder(@RequestBody @Valid PlaceOrderDTO placeOrderDto)
			throws JsonMappingException, JsonProcessingException {

		return new ResponseEntity<OrderDTO>(
				orderService.placeOrder(placeOrderDto.getOrigin(), placeOrderDto.getDestination()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/orders/{orderId}", consumes = "application/json")
	public ResponseEntity<?> patchOrder(@RequestBody @Valid PatchOrderDTO patchOrderDto,
			@PathVariable("orderId") Long orderId) {
		if (patchOrderDto.getStatus().equals(Order.Status.UNASSIGNED)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorDTO("Order status cannot be changed to UNASSIGNED"));
		}

		Optional<Order> orderById = orderRepository.findById(orderId);
		if (!orderById.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("Order Id not found"));
		}

		Order order = orderById.get();
		if (order.getStatus().equals(Order.Status.TAKEN)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Order has already been taken"));
		}

		orderService.updateStatus(order, patchOrderDto.getStatus());
		return ResponseEntity.ok(Collections.singletonMap("status", "SUCCESS"));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/orders", produces = "application/json")
	public ResponseEntity<?> getOrders(@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "5") String limit) {
		if (!page.matches("-?\\d+")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorDTO("Page number must be a valid integer"));
		}

		if (!limit.matches("-?\\d+")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Limit must be a valid integer"));
		}

		int pageNumber = Integer.parseInt(page);
		int pageSize = Integer.parseInt(limit);

		if (pageNumber < 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorDTO("Page number must be greater than or equal to 1"));
		}

		if (pageSize < 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorDTO("Limit number must be greater than or equal to 1"));
		}

		return ResponseEntity.ok(orderService.getOrders(pageNumber - 1, pageSize));
	}
}

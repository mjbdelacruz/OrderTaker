package com.lalafood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lalafood.domain.dto.Coordinate;
import com.lalafood.domain.dto.OrderDTO;
import com.lalafood.domain.entity.Order;
import com.lalafood.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private GoogleMapsService googleMapsService;

	@Autowired
	private OrderRepository orderRepository;

	public OrderDTO placeOrder(Coordinate origin, Coordinate destination)
			throws JsonMappingException, JsonProcessingException {
		Integer distance = googleMapsService.getDistanceBetweenCoordinates(origin, destination);

		Order order = new Order();
		order.setDistance(distance);
		order.setStatus(Order.Status.UNASSIGNED);

		Order savedOrder = orderRepository.save(order);

		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(savedOrder, orderDTO);

		return orderDTO;
	}

	public List<OrderDTO> getOrders(int page, int limit) {
		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<Order> orders = orderRepository.findAll(pageableRequest);

		List<OrderDTO> orderDtoList = new ArrayList<>();
		for (Order order : orders) {
			OrderDTO orderDTO = new OrderDTO();
			BeanUtils.copyProperties(order, orderDTO);

			orderDtoList.add(orderDTO);
		}

		return orderDtoList;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void updateStatus(Order order, Order.Status status) {
		order.setStatus(status);
	}
}

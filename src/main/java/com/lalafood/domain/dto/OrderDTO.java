package com.lalafood.domain.dto;

import com.lalafood.domain.entity.Order;

public class OrderDTO {
	private Long id;

	private Integer distance;

	private Order.Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Order.Status getStatus() {
		return status;
	}

	public void setStatus(Order.Status status) {
		this.status = status;
	}
}
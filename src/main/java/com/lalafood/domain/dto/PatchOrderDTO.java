package com.lalafood.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lalafood.domain.entity.Order;

public class PatchOrderDTO {

	@NotNull(message = "Status param cannot be null or empty.")
	@Pattern(regexp = "UNASSIGNED|TAKEN", message = "Invalid status value")
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public Order.Status getStatus() {
		return Order.Status.valueOf(status.toUpperCase());
	}
}

package com.lalafood.domain.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lalafood.validator.CoordinateElementsNotEmptyConstraint;

public class PlaceOrderDTO {

	@NotNull(message = "Origin param must not be null")
	@NotEmpty(message = "Origin param must not be empty")
	@Size(min = 2, max = 2, message = "Origin param must be an array of exactly two strings.")
	@CoordinateElementsNotEmptyConstraint(message = "Origin param must not contain empty or null coordinate")
	private String[] origin;

	@NotNull(message = "Destination param must not be null")
	@NotEmpty(message = "Destination param must not be empty")
	@Size(min = 2, max = 2, message = "Destination param must be an array of exactly two strings.")
	@CoordinateElementsNotEmptyConstraint(message = "Destination param must not contain empty or null coordinate")
	private String[] destination;

	@Valid
	public Coordinate getOrigin() {
		if (!isValid(this.origin)) {
			return null;
		}
		return new Coordinate(this.origin[0], this.origin[1]);
	}

	@Valid
	public Coordinate getDestination() {
		if (!isValid(this.destination)) {
			return null;
		}

		return new Coordinate(this.destination[0], this.destination[1]);
	}

	private boolean isValid(String[] coord) {
		return coord != null && coord.length == 2 && coord[0] != null && coord[1] != null && !coord[0].isEmpty()
				&& !coord[1].isEmpty();
	}
}

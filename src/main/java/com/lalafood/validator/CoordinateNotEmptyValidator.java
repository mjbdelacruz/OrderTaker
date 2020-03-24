package com.lalafood.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoordinateNotEmptyValidator
		implements ConstraintValidator<CoordinateElementsNotEmptyConstraint, String[]> {

	@Override
	public void initialize(CoordinateElementsNotEmptyConstraint contactNumber) {
	}

	@Override
	public boolean isValid(String[] coordinate, ConstraintValidatorContext context) {
		if (coordinate == null)
			return false;

		for (String latLng : coordinate) {
			if (latLng == null || latLng.isEmpty())
				return false;
		}

		return true;
	}
}

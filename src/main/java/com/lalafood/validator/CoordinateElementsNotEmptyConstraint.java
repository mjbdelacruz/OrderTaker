package com.lalafood.validator;

import com.lalafood.validator.CoordinateNotEmptyValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CoordinateNotEmptyValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CoordinateElementsNotEmptyConstraint {
	String message() default "Coordinate must not be empty or null";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

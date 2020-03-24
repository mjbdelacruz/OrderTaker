package com.lalafood.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lalafood.domain.dto.ErrorDTO;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDTO> handleMismatchedInputException(HttpMessageNotReadableException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorDTO("Cannot pass empty string in Origin/Destination param"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErrorDTO>> handleConstraintViolationException(MethodArgumentNotValidException e) {
		List<ErrorDTO> errors = e.getBindingResult().getAllErrors().stream()
				.map(error -> new ErrorDTO(error.getDefaultMessage())).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}

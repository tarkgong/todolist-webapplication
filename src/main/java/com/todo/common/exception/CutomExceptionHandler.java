package com.todo.common.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.todo.common.exception.entity.ExceptionResponseEntity;

@ControllerAdvice
public class CutomExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<Object> MethodArgumentNotValidExceptio(MethodArgumentNotValidException ex, WebRequest request) {
		ExceptionResponseEntity errorDetails = new ExceptionResponseEntity(new Date(), "VC001", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	    
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RestException.class)
	public final ResponseEntity<Object> handleRestExceptions(RestException ex, WebRequest request) {
		ExceptionResponseEntity errorDetails = new ExceptionResponseEntity(new Date(), ex.getCode(), ex.getMessage());
	    
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleRestExceptions(Exception ex, WebRequest request) {
		ExceptionResponseEntity errorDetails = new ExceptionResponseEntity(new Date(), "EX001", ex.getMessage());
	    
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

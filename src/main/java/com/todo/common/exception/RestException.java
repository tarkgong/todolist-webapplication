package com.todo.common.exception;

public class RestException extends RuntimeException{
	String message;

	public RestException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

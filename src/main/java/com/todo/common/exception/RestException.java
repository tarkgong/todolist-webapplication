package com.todo.common.exception;

public class RestException extends RuntimeException{
	String message;
	String code;

	public RestException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}

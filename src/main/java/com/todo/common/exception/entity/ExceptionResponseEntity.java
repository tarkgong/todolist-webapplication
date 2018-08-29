package com.todo.common.exception.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseEntity {
	private Date timestamp;
	private String code;
	private String message;
	
	public ExceptionResponseEntity(Date timestamp, String code, String message) {
		this.timestamp = timestamp;
		this.code = code;
		this.message = message;
	}
}

package com.todo.common.exception.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseEntity {
	private Date timestamp;
	private String message;
	
	public ExceptionResponseEntity(Date timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}
}

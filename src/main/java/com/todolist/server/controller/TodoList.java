package com.todolist.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoList {
	
	@RequestMapping("/h")
	public String index() {
		return "HellorWorld";
	}
}

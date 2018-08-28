package com.todolist.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoListViewController {
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
}
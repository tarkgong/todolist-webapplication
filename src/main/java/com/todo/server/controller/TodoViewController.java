package com.todo.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoViewController {
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
}

package com.todolist.server.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.server.dto.todo.TodoCreateRequestDto;
import com.todolist.server.dto.todo.TodoUpdateRequestDto;
import com.todolist.server.entity.todo.Todo;
import com.todolist.server.service.TodoListService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/todo")
public class TodoListRestController {
	
    private TodoListService todoListService;
    
    @GetMapping("/")
	public List<Todo> list(){
		List<Todo> todoList = todoListService.findAll();
		
		return todoList;
	}
	
    @PostMapping("/")
	public Long create(@RequestBody TodoCreateRequestDto dto) {
        return todoListService.create(dto);
	}
	
    @PutMapping("/{id}")
	public Long update(@PathVariable long id, @RequestBody TodoUpdateRequestDto dto) {
        return todoListService.update(id, dto);
	}

}

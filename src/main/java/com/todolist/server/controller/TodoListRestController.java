package com.todolist.server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.server.dto.todo.TodoCreateRequestDto;
import com.todolist.server.dto.todo.TodoPageListRequestDto;
import com.todolist.server.dto.todo.TodoPageListResponseDto;
import com.todolist.server.dto.todo.TodoUpdateRequestDto;
import com.todolist.server.dto.todo.TodoUpdateStatusRequestDto;
import com.todolist.server.entity.todo.Todo;
import com.todolist.server.entity.todo.TodoRefer;
import com.todolist.server.service.TodoListService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/todos")
public class TodoListRestController {
	
    private TodoListService todoListService;
  
    @GetMapping("/")
	public TodoPageListResponseDto listPaging(@RequestParam(value="columns[]") String[] columns, 		
										@RequestParam(value="orders[]") String[] orders, 
										TodoPageListRequestDto dto){
    	dto.setColumnList(columns);
    	dto.setOrders(orders);
    	
		TodoPageListResponseDto todoList = todoListService.findAllPaging(dto);
		
		return todoList;
	}
    
    @GetMapping("/select")
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

    @PutMapping("/{id}/status")
	public List<Todo> updateStatus(@PathVariable long id) {
        return todoListService.updateStatus(id);
	}
}

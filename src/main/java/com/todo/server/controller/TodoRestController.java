package com.todo.server.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.common.exception.RestException;
import com.todo.server.dto.todo.TodoCreateRequestDto;
import com.todo.server.dto.todo.TodoPageListRequestDto;
import com.todo.server.dto.todo.TodoPageListResponseDto;
import com.todo.server.dto.todo.TodoUpdateRequestDto;
import com.todo.server.entity.todo.Todo;
import com.todo.server.service.TodoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/todos")
public class TodoRestController {
	
    private TodoService todoListService;
  
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
		List<Todo> todoList = todoListService.findAllbyCreate();
		
		return todoList;
	}
    
    @GetMapping("/{id}/select")
	public List<Todo> list(@PathVariable long id){
		List<Todo> todoList = todoListService.findAllbyUpdate(id);
		
		return todoList;
	}
    
    @PostMapping("/")
	public Long create(@Valid @RequestBody TodoCreateRequestDto dto) {
        return todoListService.create(dto);
	}
	
    @PutMapping("/{id}")
	public Long update(@PathVariable long id, @RequestBody TodoUpdateRequestDto dto) {
        return todoListService.update(id, dto);
	}

    @PutMapping("/{id}/status")
	public Long updateStatus(@PathVariable long id) {
        return todoListService.updateStatus(id);
	}
}

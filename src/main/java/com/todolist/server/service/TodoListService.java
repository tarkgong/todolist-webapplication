package com.todolist.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todolist.server.dto.todo.TodoCreateRequestDto;
import com.todolist.server.dto.todo.TodoPageListRequestDto;
import com.todolist.server.dto.todo.TodoPageListResponseDto;
import com.todolist.server.dto.todo.TodoUpdateRequestDto;
import com.todolist.server.dto.todo.TodoUpdateStatusRequestDto;
import com.todolist.server.entity.todo.Todo;
import com.todolist.server.entity.todo.TodoRefer;
import com.todolist.server.repository.TodoReferRepository;
import com.todolist.server.repository.TodoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoListService{
    private TodoRepository todoRepository;
    private TodoReferRepository todoReferRepository;

    @Transactional(readOnly = true)
    public TodoPageListResponseDto findAllPaging(TodoPageListRequestDto dto) {
    	Page<Todo> todo = todoRepository.findAll(PageRequest.of(dto.getPage(), dto.getLength(), dto.getSort()));

    	return new TodoPageListResponseDto(todo.getContent(), todo.getTotalElements());
    }
    
    @Transactional(readOnly = true)
    public List<Todo> findAll() {
    	return todoRepository.findAllByIsfinish(false);
    }
    
    @Transactional
    public Long create(TodoCreateRequestDto dto){
    	Long id = todoRepository.save(dto.toEntity()).getId();
    	
		if(dto.getReferIds() != null){
			dto.setTodoRefers(id);
	    	todoReferRepository.saveAll(dto.getTodoRefers());
		}
    	
    	return id;
    }
    
    @Transactional
    public Long update(long id, TodoUpdateRequestDto dto){
    	dto.setId(id);
    	
    	if(dto.getTitle() != null) {
    		todoRepository.save(dto.toEntity());
    	}
    	
		if(dto.getReferIds() != null){
			dto.setTodoRefers(id);
			todoReferRepository.deleteByTodoReferIdId(id);
	    	todoReferRepository.saveAll(dto.getTodoRefers());
		}
    	
    	return id;
    }
    
    @Transactional
    public List<Todo> updateStatus(long id){    	
    	List<Todo> todos= todoRepository.findAllByTodoReferTodoReferIdReferIdAndIsfinish(id, false);
    	
    	if(todos.isEmpty()) {
    		Optional<Todo> getTodo = todoRepository.findById(id);
    		
    		Todo todo = getTodo.get();
    		
    		todo.setIsfinish(true);
    		todoRepository.save(todo);
    		
    		return todos;
    	}else {
    		return todos;
    	}
    }
}

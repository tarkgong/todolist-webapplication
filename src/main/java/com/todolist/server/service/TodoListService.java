package com.todolist.server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todolist.server.dto.todo.TodoCreateRequestDto;
import com.todolist.server.dto.todo.TodoUpdateRequestDto;
import com.todolist.server.entity.todo.Todo;
import com.todolist.server.repository.TodoReferRepository;
import com.todolist.server.repository.TodoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoListService{
    private TodoRepository todoRepository;
    private TodoReferRepository todoReferRepository;

    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return todoRepository.findAll();
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
}

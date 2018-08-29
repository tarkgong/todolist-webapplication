package com.todo.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.common.exception.RestException;
import com.todo.server.dto.todo.TodoCreateRequestDto;
import com.todo.server.dto.todo.TodoPageListRequestDto;
import com.todo.server.dto.todo.TodoPageListResponseDto;
import com.todo.server.dto.todo.TodoUpdateRequestDto;
import com.todo.server.entity.todo.Todo;
import com.todo.server.repository.TodoReferRepository;
import com.todo.server.repository.TodoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TodoService{
    private TodoRepository todoRepository;
    private TodoReferRepository todoReferRepository;

    @Transactional(readOnly = true)
    public TodoPageListResponseDto findAllPaging(TodoPageListRequestDto dto) {
    	Page<Todo> todo = todoRepository.findAll(PageRequest.of(dto.getPage(), dto.getLength(), dto.getSort()));

    	return new TodoPageListResponseDto(todo.getContent(), todo.getTotalElements());
    }
    
    @Transactional(readOnly = true)
    public List<Todo> findAllbyCreate() {
    	return todoRepository.findAllByIsfinish(false);
    }
    
    @Transactional(readOnly = true)
    public List<Todo> findAllbyUpdate(long id) {
    	return todoRepository.findTodoListForSelect(id);
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
    	
    	todoRepository.save(dto.toEntity());
    	
		if(dto.getReferIds() != null){
			dto.setTodoRefers(id);
			todoReferRepository.deleteByTodoReferIdId(id);
	    	todoReferRepository.saveAll(dto.getTodoRefers());
		}
    	
    	return id;
    }
    
    @Transactional
    public Long updateStatus(long id){    	
    	List<Todo> todos= todoRepository.findAllByTodoReferTodoReferIdReferIdAndIsfinish(id, false);
    	
    	if(todos.isEmpty()) {
    		Optional<Todo> getTodo = todoRepository.findById(id);
    		
    		Todo todo = getTodo.get();
    		
    		todo.todoFinish();
    		todoRepository.save(todo);
    		
    		return id;
    	}else {
        	throw new RestException("Not completed Todo exists.");
    	}
    }
}

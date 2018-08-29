package com.todolist.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.server.entity.todo.Todo;

public interface TodoRepository extends JpaRepository <Todo, Long>{
    List<Todo> findAllByTodoReferTodoReferIdReferIdAndIsfinish(Long referId, boolean isfinish);
	List<Todo> findAllByIsfinish(boolean isfinish);
	Page<Todo> findAll(Pageable pageable);
}

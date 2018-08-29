package com.todo.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.server.entity.todo.TodoRefer;
import com.todo.server.entity.todo.TodoReferId;

public interface TodoReferRepository extends JpaRepository <TodoRefer, TodoReferId>{
    public void deleteByTodoReferIdId(Long id);
}
package com.todolist.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.server.entity.todo.TodoRefer;
import com.todolist.server.entity.todo.TodoReferId;

public interface TodoReferRepository extends JpaRepository <TodoRefer, TodoReferId>{
    public void deleteByTodoReferIdId(Long id);
}
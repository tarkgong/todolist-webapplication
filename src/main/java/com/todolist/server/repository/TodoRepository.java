package com.todolist.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.server.entity.todo.Todo;

public interface TodoRepository extends JpaRepository <Todo, Integer>{

}

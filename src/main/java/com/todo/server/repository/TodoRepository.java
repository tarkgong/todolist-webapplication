package com.todo.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todo.server.entity.todo.Todo;

public interface TodoRepository extends JpaRepository <Todo, Long>{
    List<Todo> findAllByTodoReferTodoReferIdReferIdAndIsfinish(Long referId, boolean isfinish);
	List<Todo> findAllByIsfinish(boolean isfinish);
	
	@Query("SELECT todo from Todo todo " +
		   "LEFT OUTER JOIN todo.todoRefer todo_refer WHERE todo.id <> ?1 AND (todo_refer.todoReferId.referId <> ?1 AND todo.isfinish = false OR todo_refer.todoReferId.referId IS NULL)")
	List<Todo> findTodoListForSelect(Long id);
	//List<Todo> findAllByIdNotAndTodoReferTodoReferIdReferIdNotAndIsfinishOrTodoReferTodoReferIdReferIdIsNull(Long id);
	Page<Todo> findAll(Pageable pageable);
}

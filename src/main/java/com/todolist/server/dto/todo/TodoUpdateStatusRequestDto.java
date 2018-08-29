package com.todolist.server.dto.todo;

import java.util.ArrayList;
import java.util.List;

import com.todolist.server.entity.todo.Todo;
import com.todolist.server.entity.todo.TodoRefer;
import com.todolist.server.entity.todo.TodoReferId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoUpdateStatusRequestDto {
	private Long targetId;
	private boolean isfinish;
	
    public Todo toEntity(Todo todo){
        return Todo.builder()
        		.id(todo.getId())
        		.title(todo.getTitle())
                .isfinish(true)
                .build();
    }
}
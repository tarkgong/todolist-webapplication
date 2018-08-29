package com.todo.server.dto.todo;

import java.util.List;

import com.todo.server.entity.todo.Todo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoPageListResponseDto {
	private List<Todo> todos;
	private long total;

    public TodoPageListResponseDto(List<Todo> todos, long total) {
        this.todos = todos;
        this.total = total;
    }
	
}
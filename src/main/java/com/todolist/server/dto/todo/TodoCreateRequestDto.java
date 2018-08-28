package com.todolist.server.dto.todo;

import java.util.List;

import com.todolist.server.entity.todo.Todo;
import com.todolist.server.entity.todo.TodoRefer;
import com.todolist.server.entity.todo.TodoReferId;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoCreateRequestDto {
	private String title;
	private List<Long> referIds;
	private List<TodoRefer> todoRefers;
	
    public Todo toEntity(){
        return Todo.builder()
                .title(title)
                .isfinish(false)
                .build();
    }

	public void setTitle(String title) {
		this.title = title;
	}

	public void setReferIds(List<Long> referIds) {
		this.referIds = referIds;
	}
	
	public void setTodoRefers(Long id) {
		this.todoRefers = new ArrayList();
		
		for(long referId : this.referIds) {
			this.todoRefers.add(TodoRefer.builder()
							.todoReferId(TodoReferId
											.builder()
											.id(id)
											.referId(referId)
											.build())
							.build());		
		}
	}

}
package com.todo.server.dto.todo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.todo.server.entity.todo.Todo;
import com.todo.server.entity.todo.TodoRefer;
import com.todo.server.entity.todo.TodoReferId;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoCreateRequestDto {
	@NotEmpty(message = "Title is not empty")
	private String title;
	private List<Long> referIds;
	private List<TodoRefer> todoRefers;
	
    @Builder
    public TodoCreateRequestDto(String title, List<Long> referIds) {
        this.title = title;
        this.referIds = referIds;
    }
    
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
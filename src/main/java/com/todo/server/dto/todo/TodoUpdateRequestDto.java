package com.todo.server.dto.todo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.todo.server.entity.todo.Todo;
import com.todo.server.entity.todo.TodoRefer;
import com.todo.server.entity.todo.TodoReferId;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoUpdateRequestDto {
	@NotEmpty(message = "ID is not empty")
	private Long id;
	
	@NotEmpty(message = "Title is not empty")
	private String title;
	private List<Long> referIds;
	private TodoReferId todoReferId;
	private List<TodoRefer> todoRefers;
	
    @Builder
    public TodoUpdateRequestDto(String title, List<Long> referIds) {
        this.title = title;
        this.referIds = referIds;
    }
    
    public Todo toEntity(){
        return Todo.builder()
        		.id(id)
                .title(title)
                .isfinish(false)
                .build();
    }
	
    public TodoReferId todoReferEntity(){
        return TodoReferId
				.builder()
				.id(id)
				//.referId(referId)
				.build();
    }
    
	public void setTodoRefers(Long id) {
		this.todoRefers = new ArrayList<TodoRefer>();
		
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
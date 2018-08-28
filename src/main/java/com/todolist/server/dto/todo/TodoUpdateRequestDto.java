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
public class TodoUpdateRequestDto {
	private Long id;
	private String title;
	private List<Long> referIds;
	private TodoReferId todoReferId;
	private List<TodoRefer> todoRefers;
	
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
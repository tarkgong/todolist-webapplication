package com.todo.server.entity.todo;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todo.server.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "todo_refer")
public class TodoRefer extends BaseEntity{
	@EmbeddedId
	private TodoReferId todoReferId;
	
    @Builder
    public TodoRefer(TodoReferId todoReferId, List<Todo> todos) {
        this.todoReferId = todoReferId;
    }
}

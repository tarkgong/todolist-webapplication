package com.todolist.server.entity.todo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "todo_refer")
public class TodoRefer implements Serializable {
	@EmbeddedId
	private TodoReferId todoReferId;
	
    @Builder
    public TodoRefer(TodoReferId todoReferId) {
        this.todoReferId = todoReferId;
    }
}

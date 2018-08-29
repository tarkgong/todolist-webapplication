package com.todo.server.entity.todo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.todo.server.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "todo")
public class Todo extends BaseEntity{
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Column(length=50)
	private String title;
	
	@NotNull
	private Boolean isfinish;

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id", updatable = false)
	private List<TodoRefer> todoRefer;
	
    @Builder
    public Todo(Long id, String title, Boolean isfinish, List<TodoRefer> todoRefer) {
    	this.id = id;
        this.title = title;
        this.isfinish = isfinish;
		this.todoRefer = todoRefer;
    }
    
    public void todoFinish() {
    	this.isfinish = true;
    }
}

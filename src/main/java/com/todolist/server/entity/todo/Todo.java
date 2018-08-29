package com.todolist.server.entity.todo;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.todolist.server.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "todo")
@org.hibernate.annotations.DynamicUpdate
public class Todo extends BaseEntity{
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String title;
	
	@NotNull
	private Boolean isfinish;

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id", updatable = false)
	private Collection<TodoRefer> todoRefer;
	
    @Builder
    public Todo(Long id, String title, Boolean isfinish, Collection<TodoRefer> todoRefer) {
    	this.id = id;
        this.title = title;
        this.isfinish = isfinish;
		this.todoRefer = todoRefer;
    }
    
    public void setIsfinish(boolean isfinish) {
    	this.isfinish = isfinish;
    }
}

package com.todolist.server.entity.todo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class TodoReferId implements Serializable {
	@Column(name = "id")
	private long id;
	
	@Column(name = "refer_id")
	private long referId;
	
	@Builder
	public TodoReferId(long id, long referId) {
		this.id = id;
		this.referId = referId;
	}
}

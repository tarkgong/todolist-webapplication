package com.todo.server.dto.todo;

import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoUpdateStatusRequestDto {
	@NotEmpty(message = "Title is not empty")
	private Long targetId;
}
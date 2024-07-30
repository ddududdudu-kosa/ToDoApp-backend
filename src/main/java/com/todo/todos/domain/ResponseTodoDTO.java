package com.todo.todos.domain;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseTodoDTO {
	private String contents;
	private Long order;
	private Date date;
	private Date updateat;
	private Long categoriesId;
}

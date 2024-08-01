package com.todo.todos.domain;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class RequestTodoDTO {
	private String contents;
	private Date date;
	private Long categoriesId;
	private Date updateAt;
	private String status;
;}

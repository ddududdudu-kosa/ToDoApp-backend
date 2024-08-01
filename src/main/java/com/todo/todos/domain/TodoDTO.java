package com.todo.todos.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class TodoDTO {
    private Integer cateOrder;
    private Integer todoOrder;
    private String contents;
    private Date date;
    private String color;

    // getters and setters
}
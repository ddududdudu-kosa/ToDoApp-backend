package com.todo.todos.domain;

import java.sql.Date;

import lombok.Data;

/*
 * 작성자 : 김종현
 * 기능 : todo
 * */

@Data
public class TodoDTO {
    private Integer cateOrder;
    private Integer todoOrder;
    private String contents;
    private Date date;
    private String color;

    // getters and setters
}
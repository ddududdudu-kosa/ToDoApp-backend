package com.todo.todos.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {
    private int id;
    private int order;
    private String contents;
    private Date date;
    private String status;
    private Date startDate;
    private Date endDate;
    private String mon;
    private String tue;
    private String wen;
    private String thu;
    private String fri;
    private String sat;
    private String sun;
    private Date createAt;
    private Date updateAt;
    private int categoriesId;
    
}
package com.todo.todos.domain;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private Long id;
    private Long order;
    private String contents;
    private Date date;
    private String status;
    private Date createAt;
    private Date updateAt;
    private Long categoriesId;
}
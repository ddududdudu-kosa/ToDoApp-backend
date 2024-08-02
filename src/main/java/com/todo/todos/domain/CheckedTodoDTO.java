package com.todo.todos.domain;

import java.util.List;

import lombok.Data;

/*
 * 작성자 : 김종현
 * 기능 : todo
 * */

@Data
public class CheckedTodoDTO {
    private Long memberId;
    private String nickname;
    private String profileImg;
    private String privacy;
    private List<TodoDTO> checkedTodos;

    // getters and setters
}

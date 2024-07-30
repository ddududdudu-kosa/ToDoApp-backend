package com.todo.todos.domain;

import java.util.List;

import lombok.Data;

@Data
public class CheckedTodoDTO {
    private Long memberId;
    private String nickname;
    private String profileImg;
    private String privacy;
    private List<TodoDTO> checkedTodos;

    // getters and setters
}

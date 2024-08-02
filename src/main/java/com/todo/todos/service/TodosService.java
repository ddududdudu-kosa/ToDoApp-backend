package com.todo.todos.service;

import java.util.Date;
import java.util.List;

import com.todo.todos.domain.CheckedTodoDTO;
import com.todo.todos.domain.Todo;

/*
 * 작성자 : 김종현
 * 기능 : todo
 * */

public interface TodosService {
	List<Todo> getTodosByMemberId(Long memberId);
    Long addTodo(Todo todo);
    Todo updateTodo(Todo todo);
    void deleteTodo(Long id);
    Long findByTodoMaxOrder(Long categoryId);
    Todo findByTodoId(Long TodoId);
    void updateOrderOnDelete(Long categoriesId, Long order);
	List<Todo> findTodosByUserIdAndDate(Long userId, Date date);
	

	List<CheckedTodoDTO> getCheckedTodos(int page, int size);
}

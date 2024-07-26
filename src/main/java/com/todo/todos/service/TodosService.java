package com.todo.todos.service;

import java.util.Date;
import java.util.List;

import com.todo.todos.domain.Todo;

public interface TodosService {
	Todo getTodoById(Long id);
	List<Todo> getAllTodos();
	boolean createTodo(Todo todo);
	boolean updateTodo(Todo todo);
	boolean deleteTodoById(Long id, Long order, Long categoriesId, Date date);
	
}

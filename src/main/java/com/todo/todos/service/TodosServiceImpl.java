package com.todo.todos.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.todos.domain.Todo;
import com.todo.todos.mapper.TodosMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class TodosServiceImpl implements TodosService {

	private final TodosMapper todosMapper;

	@Override
	public Todo getTodoById(Long id) {
		return todosMapper.selectTodoById(id);
	}

	@Override
	public List<Todo> getAllTodos() {
		return todosMapper.selectAllTodos();
	}

	@Override
	public boolean createTodo(Todo todo) {
		return todosMapper.insertTodo(todo) > 0;
	}

	@Override
	public boolean updateTodo(Todo todo) {
		return todosMapper.updateTodo(todo) > 0;
	}

	@Override
	@Transactional
	public boolean deleteTodoById(Long id, Long order, Long categoriesId, Date date) {
		return todosMapper.deleteTodoById(id, order, categoriesId, date) > 0;
	}

}

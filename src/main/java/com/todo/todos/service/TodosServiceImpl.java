package com.todo.todos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.todos.domain.Todo;
import com.todo.todos.mapper.TodosMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class TodosServiceImpl implements TodosService {

	private final TodosMapper todoMapper;
	
	@Override
	public Long addTodo(Todo todo) {
        return todoMapper.insertTodo(todo);
	}

	@Override
	public List<Todo> getTodosByMemberId(Long memberId) {
		return todoMapper.findTodosByMemberId(memberId);
	}

	@Override
	public Todo updateTodo(Todo todo) {
		todoMapper.updateTodo(todo);
        return todoMapper.findByTodoId(todo.getId());
	}

	@Override
	public void deleteTodo(Long id) {
		todoMapper.deleteTodo(id);
	}

	@Override
	public Long findByTodoMaxOrder(Long categoryId) {
		return todoMapper.findByTodoMaxOrder(categoryId);
	}

	@Override
	public Todo findByTodoId(Long todoId) {
		return todoMapper.findByTodoId(todoId);
	}

	@Override
	public void updateOrderOnDelete(Long categoriesId, Long order) {
		todoMapper.updateOrderOnDelete(categoriesId, order);
	}

	@Override
	public List<Todo> findTodosByUserIdAndDate(Long memberId, Date date) {
		return todoMapper.findTodosByUserIdAndDate(memberId, date);
	}
}

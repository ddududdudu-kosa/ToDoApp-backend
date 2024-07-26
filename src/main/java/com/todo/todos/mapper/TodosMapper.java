package com.todo.todos.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.todo.todos.domain.Todo;

@Mapper
public interface TodosMapper {
	Todo selectTodoById(Long id);
	List<Todo> selectAllTodos();
	Long insertTodo(Todo todo);
	Long deleteTodoById(Long id, Long order, Long categoriesId, Date date);
	Long updateTodo(Todo todo);
}

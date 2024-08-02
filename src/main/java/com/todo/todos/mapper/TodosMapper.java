package com.todo.todos.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.todo.todos.domain.CheckedTodoDTO;
import com.todo.todos.domain.Todo;

/*
 * 작성자 : 김종현
 * 기능 : todo
 * */

@Mapper
public interface TodosMapper {
	    List<Todo> findTodosByMemberId(Long memberId);
	    Long insertTodo(Todo todo);
	    void updateTodo(Todo todo);
	    void deleteTodo(Long id);
		Long getMaxOrder(@Param("memberId") Long memberId, @Param("categoriesId") Long categoriesId);
		void reorderTodos(Map<String, Long> of);
		Todo findByTodoId(Long TodoId);
		Long findByTodoMaxOrder(Long categoryId);
		void updateOrderOnDelete(@Param("categoriesId") Long categoriesId, @Param("order") Long order);
		List<Todo> findTodosByUserIdAndDate(@Param("memberId")Long memberId, @Param("date") Date date);


		List<CheckedTodoDTO> findRecentCompletedTodos(@Param("page") int page, @Param("size") int size);
		
}


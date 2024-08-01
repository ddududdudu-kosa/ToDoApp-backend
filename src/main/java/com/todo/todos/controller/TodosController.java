package com.todo.todos.controller;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todos.domain.CheckedTodoDTO;
import com.todo.todos.domain.RequestTodoDTO;
import com.todo.todos.domain.ResponseTodoDTO;
import com.todo.todos.domain.Todo;
import com.todo.todos.service.TodosService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
@Log4j2
public class TodosController {
	
	private final TodosService todoService;
	
	// todo 내용 생성 로직
	@PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
		Long maxOrder = todoService.findByTodoMaxOrder(todo.getCategoriesId()) + 1;
		log.info("todo.getCategoriesId() : " + todo.getCategoriesId());
        Todo newTodo = Todo.builder()
        		.contents(todo.getContents())
        		.order(maxOrder)
        		.date(todo.getDate())
        		.categoriesId(todo.getCategoriesId())
        		.build();
        
        Long savedTodoId = todoService.addTodo(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }
	
	@GetMapping("/member/{memberId}")
    public ResponseEntity<List<Todo>> getTodosByMemberId(@PathVariable @Param("memberId") Long memberId) {
        return ResponseEntity.ok(todoService.getTodosByMemberId(memberId));
	}
	
	//todo 업데이트 로직

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTodoDTO> updateTodo(@RequestBody RequestTodoDTO requestTodoDTO, @PathVariable("id") @Param("id") Long id) {
        Todo todo = Todo.builder()
        		.id(id)
        		.contents(requestTodoDTO.getContents())
        		.date(requestTodoDTO.getDate())
        		.categoriesId(requestTodoDTO.getCategoriesId())
        		.status(requestTodoDTO.getStatus())
        		.build();
        Todo updateTodo = todoService.updateTodo(todo);
        ResponseTodoDTO responseTodoDTO = ResponseTodoDTO.builder()
        		.contents(updateTodo.getContents())
        		.date(updateTodo.getDate())
        		.categoriesId(updateTodo.getCategoriesId())
        		.updateat(updateTodo.getUpdateAt())
        		.status(updateTodo.getStatus())
        		.build();
        return ResponseEntity.ok(responseTodoDTO);        
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
    	Todo todo = todoService.findByTodoId(id);
    	todoService.deleteTodo(id);
    	
    	todoService.updateOrderOnDelete(todo.getCategoriesId(), todo.getOrder());
    	
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/date/{memberId}/{date}")
    public ResponseEntity<List<Todo>> getTodosByDateAndUserId(@PathVariable(name = "memberId") @Param("memberId") Long memberId, 
                                                              @PathVariable(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<Todo> todos = todoService.findTodosByUserIdAndDate(memberId, date);
        return ResponseEntity.ok(todos);
    }
    
    
    @GetMapping("/checkedList")
    public ResponseEntity<List<CheckedTodoDTO>> getCheckedTodos(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "size", defaultValue = "10") int size) {

    	//int fixedSize = 10; 페이지 크기를 10으로 고정  위에 @RequestParam에서 size 제거시
        List<CheckedTodoDTO> todos = todoService.getCheckedTodos(page, size);
        if (todos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todos);
    }


}

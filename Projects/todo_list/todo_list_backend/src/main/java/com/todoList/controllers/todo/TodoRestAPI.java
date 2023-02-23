package com.todoList.controllers.todo;

import com.todoList.entities.Todo;
import com.todoList.services.todo.TodoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todo")
public class TodoRestAPI {

    private final TodoServiceImpl todoService;

    @GetMapping
    List<Todo> getTodos() {
        return todoService.getAll();
    }

    @GetMapping("/{todoId}")
    Todo getTodo(@PathVariable int todoId) {
        Todo tempTodo = todoService.findById(todoId);
        if(tempTodo == null)
            throw new RuntimeException("Todo id not found - " + todoId);
        return tempTodo;
    }

    @PostMapping
    Todo saveTodo(@RequestBody Todo todo) {
        System.out.println(todo);
        todoService.save(todo);
        return todo;
    }

    @PutMapping
    Todo updateTodo(@RequestBody Todo todo) {
        todoService.save(todo);
        return todo;
    }

    @DeleteMapping("/{todoId}")
    Todo deleteTodo(@PathVariable int todoId) {
        Todo tempTodo = todoService.findById(todoId);
        if(tempTodo == null)
            throw new RuntimeException("Todo id not found - " + todoId);
        todoService.deleteById(todoId);
        return tempTodo;
    }
}

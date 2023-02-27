package com.todoList.controllers.todo;

import com.todoList.services.jwt.JwtService;
import com.todoList.entities.Todo;
import com.todoList.services.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todo")
public class TodoRestAPI {

    private final TodoService todoService;
    private final JwtService jwtService;

    @GetMapping
    List<Todo> getTodos(
            @RequestParam(value = "from", required = false, defaultValue = "1") int from,
            @RequestParam(value = "to", required = false, defaultValue = "10") int to
    ) {
        return todoService.getAll(from, to);
    }

    @GetMapping("/{todoId}")
    ResponseEntity<?> getTodo(@PathVariable int todoId) {
        Todo tempTodo = todoService.findById(todoId);
        if(tempTodo == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tempTodo);
    }

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo) throws Exception {
        return todoService.save(todo);
    }

    @PutMapping
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
        Todo updatedTodo = todoService.update(todo);
        if(updatedTodo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{todoId}")
    ResponseEntity<?> deleteTodo(@PathVariable int todoId) {
        Todo tempTodo = todoService.findById(todoId);
        if(tempTodo == null)
            return ResponseEntity.notFound().build();
        todoService.deleteById(todoId);
        return ResponseEntity.ok(tempTodo);
    }
}

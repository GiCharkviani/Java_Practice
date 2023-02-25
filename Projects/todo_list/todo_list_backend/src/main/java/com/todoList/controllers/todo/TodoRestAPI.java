package com.todoList.controllers.todo;

import com.todoList.configuration.JwtService;
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
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(value = "from", required = false, defaultValue = "1") int from,
            @RequestParam(value = "to", required = false, defaultValue = "10") int to
    ) {
        String userEmail = jwtService.extractUserEmail(authorizationHeader.substring(7));
        return todoService.getAll(userEmail, from, to);
    }

    @GetMapping("/{todoId}")
    ResponseEntity<?> getTodo(@PathVariable int todoId, @RequestHeader("Authorization") String authorizationHeader) {
        String userEmail = jwtService.extractUserEmail(authorizationHeader.substring(7));
        Todo tempTodo = todoService.findById(todoId, userEmail);
        if(tempTodo == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tempTodo);
    }

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo, @RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String userEmail = jwtService.extractUserEmail(authorizationHeader.substring(7));
        return todoService.save(todo, userEmail);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable int todoId, @RequestHeader("Authorization") String authorizationHeader) {
        String userEmail = jwtService.extractUserEmail(authorizationHeader.substring(7));
        Todo updatedTodo = todoService.update(todoId, todo, userEmail);
        if(updatedTodo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
        }
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{todoId}")
    ResponseEntity<?> deleteTodo(@PathVariable int todoId, @RequestHeader("Authorization") String authorizationHeader) {
        String userEmail = jwtService.extractUserEmail(authorizationHeader.substring(7));
        Todo tempTodo = todoService.findById(todoId, userEmail);
        if(tempTodo == null)
            return ResponseEntity.notFound().build();
        todoService.deleteById(todoId, userEmail);
        return ResponseEntity.ok(tempTodo);
    }
}

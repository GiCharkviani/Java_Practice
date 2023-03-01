package com.todoList.controllers.todo;

import com.todoList.controllers.todo.helpers.TodoRequest;
import com.todoList.controllers.todo.helpers.TodoStatusRequest;
import com.todoList.entities.Todo;
import com.todoList.services.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todo")
public class TodoRestAPI {

    private final TodoService todoService;

    @GetMapping
    List<Todo> getTodos(
            @RequestParam(value = "from", required = false, defaultValue = "1") int from,
            @RequestParam(value = "to", required = false, defaultValue = "10") int to
    ) {
        return todoService.getAllLimited(from, to);
    }

    @GetMapping("/{todoId}")
    ResponseEntity<?> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.get(todoId));
    }

    @PostMapping
    public Todo saveTodo(@RequestBody TodoRequest todo) {
        return todoService.save(todo);
    }

    @PutMapping
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.update(todo));
    }

    @PatchMapping
    public ResponseEntity<?> updateTodoStatus(@RequestBody TodoStatusRequest todoStatusRequest) {
        todoService.updateStatus(todoStatusRequest.getId(), todoStatusRequest.getStatus());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{todoId}")
    ResponseEntity<?> deleteTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.delete(todoId));
    }
}

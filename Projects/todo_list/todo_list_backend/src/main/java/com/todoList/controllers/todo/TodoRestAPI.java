package com.todoList.controllers.todo;

import com.todoList.controllers.todo.DTOs.*;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Order;
import com.todoList.enums.todo.Priority;
import com.todoList.enums.todo.SortBy;
import com.todoList.enums.todo.Status;
import com.todoList.services.todo.TodoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todo")
@SecurityRequirement(name = "Bearer Authentication")
public class TodoRestAPI {

    private final TodoService todoService;

    @GetMapping
    TodoResponseDTO getTodos(
            @RequestParam(value = "from", defaultValue = "1") int from,
            @RequestParam(value = "to", defaultValue = "10") int to,
            @RequestParam(value = "todo", required = false) String todo,
            @RequestParam(value = "status", required = false) Status status,
            @RequestParam(value = "priority", required = false) Priority priority,
            @RequestParam(value = "sortBy", required = false) SortBy sortBy,
            @RequestParam(value = "order", required = false) Order order
            ) {
        TodoQueryParamDTO todoQueryParamDTO = TodoQueryParamDTO
                .builder()
                .to(to).from(from).todo(todo)
                .status(status).priority(priority)
                .sortBy(sortBy).order(order)
                .build();

        return todoService.getAllLimited(todoQueryParamDTO);
    }

    @GetMapping("/{todoId}")
    ResponseEntity<Todo> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.get(todoId));
    }

    @PostMapping
    public Todo saveTodo(@Valid @RequestBody TodoAddRequestDTO todo) {
        return todoService.save(todo);
    }

    @PutMapping
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoEditRequestDTO todo) {
        return ResponseEntity.ok(todoService.update(todo));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateTodoStatus(@RequestBody TodoStatusUpdateRequestDTO todoStatusRequest) {
        todoService.updateStatus(todoStatusRequest.getId(), todoStatusRequest.getStatus());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/priority")
    public ResponseEntity<?> updatePriority(@RequestBody TodoPriorityUpdateRequestDTO todoStatusRequest) {
        todoService.updatePriority(todoStatusRequest.getId(), todoStatusRequest.getPriority());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{todoId}")
    ResponseEntity<?> deleteTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.delete(todoId));
    }
}

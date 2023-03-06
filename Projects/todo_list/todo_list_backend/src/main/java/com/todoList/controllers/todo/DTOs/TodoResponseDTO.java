package com.todoList.controllers.todo.DTOs;

import com.todoList.entities.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TodoResponseDTO {
    private long totalCount;
    private List<Todo> todos;
}

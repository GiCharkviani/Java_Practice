package com.todoList.controllers.todo.helpers;

import com.todoList.enums.todo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {
    private String whatTodo;
    private LocalDateTime whenTodo;
    private Status status;
}

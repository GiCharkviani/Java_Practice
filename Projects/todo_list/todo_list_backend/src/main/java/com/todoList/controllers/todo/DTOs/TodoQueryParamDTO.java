package com.todoList.controllers.todo.DTOs;

import com.todoList.enums.todo.Order;
import com.todoList.enums.todo.Priority;
import com.todoList.enums.todo.SortBy;
import com.todoList.enums.todo.Status;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoQueryParamDTO {
    private int from;
    private int to;
    @Nullable
    private String todo;
    @Nullable
    private Status status;
    @Nullable
    private Priority priority;
    @Nullable
    private SortBy sortBy;
    @Nullable
    private Order order;
}

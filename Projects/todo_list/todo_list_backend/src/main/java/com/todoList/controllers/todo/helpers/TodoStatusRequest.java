package com.todoList.controllers.todo.helpers;

import com.todoList.enums.todo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoStatusRequest {
    int id;
    Status status;
}

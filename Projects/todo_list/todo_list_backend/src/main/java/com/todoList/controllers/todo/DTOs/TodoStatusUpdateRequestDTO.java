package com.todoList.controllers.todo.DTOs;

import com.todoList.enums.todo.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoStatusUpdateRequestDTO {
    @NotNull
    @Positive
    private int id;

    @NotNull
    private Status status;
}

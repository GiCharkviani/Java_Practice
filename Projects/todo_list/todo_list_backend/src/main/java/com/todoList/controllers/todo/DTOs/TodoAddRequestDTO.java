package com.todoList.controllers.todo.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.todoList.AOP.customHandlers.deserializers.DateDeserializer;
import com.todoList.enums.todo.Priority;
import com.todoList.enums.todo.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoAddRequestDTO {
    @NotBlank
    private String whatTodo;

    @NotNull
    @FutureOrPresent
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime whenTodo;

    @NotNull
    private Status status;

    @NotNull
    private Priority priority;
}

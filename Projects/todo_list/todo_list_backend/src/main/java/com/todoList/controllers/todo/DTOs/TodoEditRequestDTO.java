package com.todoList.controllers.todo.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.todoList.AOP.customHandlers.deserializers.DateDeserializer;
import com.todoList.enums.todo.Priority;
import com.todoList.enums.todo.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoEditRequestDTO {
    @NotNull
    @Positive
    private int id;

    @Nullable
    private String whatTodo;

    @Nullable
    @FutureOrPresent
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime whenTodo;

    @Nullable
    private Status status;

    @Nullable
    private Priority priority;
}

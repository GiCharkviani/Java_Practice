package com.todoList.AOP.customHandlers.responseObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomBaseResponse {
    private int status;
    private String message;
}

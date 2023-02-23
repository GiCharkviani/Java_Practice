package com.todoList.AOP.Exceptions.ResponseObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}

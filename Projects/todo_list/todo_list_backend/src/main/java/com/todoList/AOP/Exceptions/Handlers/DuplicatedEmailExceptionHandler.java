package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DuplicatedEmailExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(DuplicatedEmailException duplicatedEmailException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message("The uses is already registered by the same email: " + duplicatedEmailException.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnauthorizedNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(UnauthorizedNotFoundException unauthorizedUserException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(unauthorizedUserException.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.UNAUTHORIZED);
    }
}

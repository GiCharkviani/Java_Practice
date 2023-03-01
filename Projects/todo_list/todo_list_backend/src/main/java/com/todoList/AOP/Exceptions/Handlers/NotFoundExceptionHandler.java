package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(NotFoundException notFoundException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(notFoundException.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.NOT_FOUND);
    }
}

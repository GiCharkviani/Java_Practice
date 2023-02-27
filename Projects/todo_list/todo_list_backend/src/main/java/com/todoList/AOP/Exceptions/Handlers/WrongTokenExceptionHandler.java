package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ExceptionObjects.WrongTokenException;
import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WrongTokenExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(WrongTokenException wrongTokenException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message("Token has invalid format")
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

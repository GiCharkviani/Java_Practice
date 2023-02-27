package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerSideExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(RuntimeException runtimeException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(runtimeException.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidDataExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(ConstraintViolationException invalidDataException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Provided data is invalid")
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }
}

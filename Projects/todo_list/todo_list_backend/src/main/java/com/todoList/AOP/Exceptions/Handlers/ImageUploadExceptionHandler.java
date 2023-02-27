package com.todoList.AOP.Exceptions.Handlers;

import com.todoList.AOP.Exceptions.ExceptionObjects.ImageUploadingException;
import com.todoList.AOP.Exceptions.ResponseObjects.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ImageUploadExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseErrorResponse> handleException(ImageUploadingException imageUploadingException) {
        BaseErrorResponse baseErrorResponse = BaseErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(imageUploadingException.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(baseErrorResponse, HttpStatus.BAD_REQUEST);
    }
}

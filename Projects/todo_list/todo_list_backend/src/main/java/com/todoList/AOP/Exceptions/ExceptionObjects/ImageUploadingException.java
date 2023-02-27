package com.todoList.AOP.Exceptions.ExceptionObjects;

import java.io.IOException;

public class ImageUploadingException extends IOException {

    public ImageUploadingException() {
        super();
    }

    public ImageUploadingException(String message) {
        super(message);
    }

    public ImageUploadingException(String message, Throwable cause) {
        super(message, cause);
    }
}

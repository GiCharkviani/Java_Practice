package com.todoList.AOP.Exceptions.ExceptionObjects;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException() {
    }

    public NotFoundException(String s) {
        super(s);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

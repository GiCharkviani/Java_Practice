package com.todoList.AOP.Exceptions.ExceptionObjects;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
    }

    public WrongTokenException(String message) {
        super(message);
    }

    public WrongTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

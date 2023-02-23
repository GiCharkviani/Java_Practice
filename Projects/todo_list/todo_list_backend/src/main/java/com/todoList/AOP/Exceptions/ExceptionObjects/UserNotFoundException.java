package com.todoList.AOP.Exceptions.ExceptionObjects;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {
    public UserNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public UserNotFoundException(String reason) {
        super(reason);
    }
}

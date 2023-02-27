package com.todoList.AOP.Exceptions.ExceptionObjects;

import java.sql.SQLException;

public class UnauthorizedNotFoundException extends SQLException {
    public UnauthorizedNotFoundException() {
    }

    public UnauthorizedNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public UnauthorizedNotFoundException(String reason) {
        super(reason);
    }
}

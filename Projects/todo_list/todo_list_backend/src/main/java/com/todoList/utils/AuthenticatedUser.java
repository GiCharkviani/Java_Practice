package com.todoList.utils;

import com.todoList.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {
    public static User user() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

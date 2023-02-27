package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.User;

public interface UserService {
    User save(User user) throws Exception;
    User getByEmail(String email) throws UnauthorizedNotFoundException;
}

package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.entities.User;

public interface UserService {
    User save(User user) throws Exception;
    User getByEmail(String email) throws UnauthorizedNotFoundException;
    User update(UserRequest user) throws Exception;
}

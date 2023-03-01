package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.entities.User;

public interface UserService {
    User save(User user);
    User getByEmail(String email) throws UnauthorizedNotFoundException;
    User getById(int id);
    User update(UserRequest user) throws Exception;
    Boolean checkIfExists(String email);
    void delete() throws Exception;
}

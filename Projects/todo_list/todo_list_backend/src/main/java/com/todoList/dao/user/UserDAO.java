package com.todoList.dao.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.entities.User;

public interface UserDAO {
    User save(User user);
    User update(UserRequest user) throws Exception;
    User getByEmail(String email) throws UnauthorizedNotFoundException;
    User getById(long id);
    Boolean checkIfEmailExists(String email);
    void delete(long id);
}

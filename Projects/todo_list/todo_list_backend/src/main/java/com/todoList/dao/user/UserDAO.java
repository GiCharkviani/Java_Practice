package com.todoList.dao.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.User;

public interface UserDAO {
    User save(User user);
    User getByEmail(String email) throws UnauthorizedNotFoundException;
    User getById(long id) throws NotFoundException;
    Boolean checkIfEmailExists(String email);
    void delete(long id) throws NotFoundException;
}

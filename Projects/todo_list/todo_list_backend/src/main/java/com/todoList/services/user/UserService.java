package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.DTOs.UserEditRequestDTO;
import com.todoList.entities.User;

public interface UserService {
    User save(User user);
    User getByEmail(String email) throws UnauthorizedNotFoundException;
    User getById(long id);
    User update(UserEditRequestDTO user) throws NotFoundException;
    Boolean checkIfExists(String email);
    void delete() throws NotFoundException, UnauthorizedNotFoundException;
}

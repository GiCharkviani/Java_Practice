package com.todoList.dao.user;

import com.todoList.entities.User;

public interface UserDAO {
    User save(User user);
    User getByEmail(String email);
    User getById(int id);
    Boolean checkIfEmailExists(String email);
}

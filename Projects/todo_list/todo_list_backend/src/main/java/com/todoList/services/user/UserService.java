package com.todoList.services.user;

import com.todoList.entities.Token;
import com.todoList.entities.User;

public interface UserService {
    User save(User user);
    User getByEmail(String email);
}

package com.todoList.services.user;

import com.todoList.dao.user.UserDAO;
import com.todoList.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    @Transactional
    public User save(User user) {
        return this.userDAO.save(user);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        return this.userDAO.getByEmail(email);
    }
}

package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.dao.user.UserDAO;
import com.todoList.entities.User;
import com.todoList.services.image.ImageService;
import com.todoList.utils.AuthenticatedUser;
import com.todoList.utils.Base64Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ImageService imageService;

    @Override
    @Transactional
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public User getByEmail(String email) throws UnauthorizedNotFoundException {
        return userDAO.getByEmail(email);
    }

    @Override
    @Transactional
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    @Transactional
    public Boolean checkIfExists(String email) {
        return userDAO.checkIfEmailExists(email);
    }

    @Override
    @Transactional
    public User update(UserRequest userRequest) throws Exception {
        User updatedUser = userDAO.update(userRequest);
        imageService.delete(updatedUser.getId());
        imageService.uploadImage(Base64Util.imageEntity(userRequest.getImage(), updatedUser));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                updatedUser,
                null,
                Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);


        return updatedUser;
    }

    @Override
    @Transactional
    public void delete() {
        int userId = AuthenticatedUser.user().getId();
        imageService.delete(userId);
        userDAO.delete(userId);
    }
}

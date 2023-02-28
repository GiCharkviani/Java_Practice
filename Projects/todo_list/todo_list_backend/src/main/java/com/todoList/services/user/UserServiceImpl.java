package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ImageService imageService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    @Transactional
    public User getByEmail(String email) throws UnauthorizedNotFoundException {
        User user = userDAO.getByEmail(email);
        if(user == null)
            throw new UnauthorizedNotFoundException("User was not found");

        return user;
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
        boolean userExists = userDAO.checkIfEmailExists(userRequest.getEmail());

        if(userExists)
            throw new DuplicatedEmailException(userRequest.getEmail());

        User user = userDAO.getById(AuthenticatedUser.user().getId());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        imageService.remove(user.getId());

        imageService.uploadImage(Base64Util.imageEntity(userRequest.getImage(), user));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);


        return save(user);
    }
}

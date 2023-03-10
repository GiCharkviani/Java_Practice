package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.DTOs.UserEditRequestDTO;
import com.todoList.daos.user.UserDAO;
import com.todoList.entities.Todo;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.services.image.ImageService;
import com.todoList.services.todo.TodoService;
import com.todoList.services.token.TokenService;
import com.todoList.utils.AuthenticatedUser;
import com.todoList.utils.Base64Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ImageService imageService;
    private final TodoService todoService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;


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
    public User getById(long id) {
        return userDAO.getById(id);
    }

    @Override
    @Transactional
    public Boolean checkIfExists(String email) {
        return userDAO.checkIfEmailExists(email);
    }

    @Override
    @Transactional
    public User update(UserEditRequestDTO userRequest) throws NotFoundException {
        boolean userExists = userDAO.checkIfEmailExists(userRequest.getEmail());

        if(userExists)
            throw new DuplicatedEmailException(userRequest.getEmail());

        User user = getById(userRequest.getId());

        if(userRequest.getFirstname() != null && !userRequest.getFirstname().isEmpty())
            user.setFirstname(userRequest.getFirstname());

        if(userRequest.getLastname() != null && !userRequest.getLastname().isEmpty())
            user.setLastname(userRequest.getLastname());

        if(userRequest.getEmail() != null && !userRequest.getEmail().isEmpty())
            user.setEmail(userRequest.getEmail());

        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty())
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User updatedUser = userDAO.save(user);

        if(userRequest.getImage() != null) {
            imageService.delete(updatedUser.getId());
            imageService.uploadImage(Base64Util.imageEntity(userRequest.getImage(), updatedUser));
        }

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
    public void delete() throws NotFoundException, UnauthorizedNotFoundException {
        long userId = AuthenticatedUser.user().getId();
        List<Todo> todos = todoService.getAll();
        List<Token> tokens = tokenService.getAll();

        for(Todo todo: todos) {
            todoService.delete(todo.getId());
        }
        for(Token token: tokens) {
            tokenService.delete(token.getToken());
        }

        imageService.delete(userId);
        userDAO.delete(userId);
    }
}

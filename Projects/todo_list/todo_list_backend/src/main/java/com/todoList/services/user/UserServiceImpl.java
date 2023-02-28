package com.todoList.services.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.auth.helpers.ImageBase64;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.dao.user.UserDAO;
import com.todoList.entities.Image;
import com.todoList.entities.User;
import com.todoList.services.image.ImageService;
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
    public User update(UserRequest user) throws Exception {
        boolean userExists = userDAO.checkIfExists(user.getEmail());

        if(userExists)
            throw new DuplicatedEmailException(user.getEmail());

        User foundUser = userDAO.getByEmail(getAuthenticatedUser().getEmail());
        int foundUserImageId = foundUser.getImage().getId();

        ImageBase64 userRequestImage = user.getImage();
        Image updatedImage = imageService
                .uploadImage(
                        Base64Util.decode(userRequestImage.getImage()),
                        userRequestImage.getName(),
                        userRequestImage.getType()
                );

        foundUser.setImage(updatedImage);
        foundUser.setFirstname(user.getFirstname());
        foundUser.setLastname(user.getLastname());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(passwordEncoder.encode(user.getPassword()));

        imageService.remove(foundUserImageId);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                foundUser,
                null,
                Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return save(foundUser);
    }

    @Override
    public Boolean checkIfExists(String email) {
        return userDAO.checkIfExists(email);
    }

    private User getAuthenticatedUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

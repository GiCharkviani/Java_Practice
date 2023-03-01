package com.todoList.services.auth;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.controllers.auth.helpers.AuthenticationRequest;
import com.todoList.controllers.auth.helpers.AuthenticationResponse;
import com.todoList.controllers.auth.helpers.RegisterRequest;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.services.image.ImageService;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
import com.todoList.utils.Base64Util;
import com.todoList.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final ImageService imageService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;


    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        boolean userExists = userService.checkIfExists(request.getEmail());

        if(userExists)
            throw new DuplicatedEmailException(request.getEmail());

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        final User savedUser = userService.save(user);
        final String jwtToken = generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        imageService.uploadImage(Base64Util.imageEntity(request.getImage(), savedUser));

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        final User user = userService.getByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid email or password");
        }

        final String jwtToken = generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .build();

        tokenService.save(token);
    }

    private String generateToken(User user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("id", Integer.toString(user.getId()));
        userMap.put("email", user.getEmail());
        userMap.put("firstname", user.getFirstname());
        userMap.put("lastname", user.getLastname());

        return JwtUtil.generateToken(userMap);
    }
}

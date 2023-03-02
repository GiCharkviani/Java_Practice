package com.todoList.services.auth;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.auth.DTOs.AuthenticationResponseDTO;
import com.todoList.controllers.auth.DTOs.ImageBase64DTO;
import com.todoList.controllers.auth.DTOs.LoginRequestDTO;
import com.todoList.controllers.auth.DTOs.RegisterRequestDTO;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.services.image.ImageService;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
import com.todoList.utils.Base64Util;
import com.todoList.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
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


    public AuthenticationResponseDTO register(RegisterRequestDTO request) throws Exception {
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

        ImageBase64DTO imageBase64 = request.getImage();

        if(imageBase64 != null)
            imageService.uploadImage(Base64Util.imageEntity(imageBase64, savedUser));

        return AuthenticationResponseDTO
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDTO login(LoginRequestDTO request) throws Exception {
        final User user = userService.getByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedNotFoundException("Invalid email or password");
        }

        final String jwtToken = generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponseDTO
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
        userMap.put("id", Long.toString(user.getId()));
        userMap.put("email", user.getEmail());
        userMap.put("firstname", user.getFirstname());
        userMap.put("lastname", user.getLastname());

        return JwtUtil.generateToken(userMap);
    }
}

package com.todoList.services.auth;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.AOP.Exceptions.ExceptionObjects.ImageUploadingException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.auth.helpers.AuthenticationRequest;
import com.todoList.controllers.auth.helpers.AuthenticationResponse;
import com.todoList.controllers.auth.helpers.ImageOnRegister;
import com.todoList.controllers.auth.helpers.RegisterRequest;
import com.todoList.entities.Image;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.entities.enums.TokenType;
import com.todoList.services.image.ImageService;
import com.todoList.services.jwt.JwtService;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final ImageService imageService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;


    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        Image uploadImage;
        String jwtToken;
        User savedUser;
        try {
            ImageOnRegister image = request.getImage();
            byte[] imageBytes = Base64.getDecoder().decode(image.getBase64Image());

            uploadImage  = imageService.uploadImage(imageBytes, image.getName(), image.getType());

            User user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .image(uploadImage)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

            try {
                savedUser = userService.save(user);
            } catch (Exception e) {
                throw new DuplicatedEmailException(user.getEmail());
            }

            jwtToken = jwtService.generateToken(user);

            saveUserToken(savedUser, jwtToken);
        } catch (IOException ioException) {
            throw new ImageUploadingException("An image couldn't be processed. Please make sure that you are providing right value");
        }

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        User user = userService.getByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedNotFoundException("User was not found");
        }

        String jwtToken = jwtService.generateToken(user);

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
                .tokenType(TokenType.BEARER)
                .build();

        tokenService.save(token);
    }
}

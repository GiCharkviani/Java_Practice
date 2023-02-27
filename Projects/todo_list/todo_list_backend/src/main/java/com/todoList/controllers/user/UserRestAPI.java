package com.todoList.controllers.user;

import com.todoList.controllers.auth.helpers.ImageBase64;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.controllers.user.helpers.UserResponse;
import com.todoList.entities.Image;
import com.todoList.entities.User;
import com.todoList.services.jwt.JwtService;
import com.todoList.services.user.UserService;
import com.todoList.utils.Base64Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserRestAPI {

    private final UserService userService;
    private final JwtService jwtService;


    @GetMapping
    public ResponseEntity<?> getUser() {
        User tempUser = getAuthenticatedUser();
        Image tempImage = tempUser.getImage();

        UserResponse userResponse = UserResponse
                .builder()
                .firstname(tempUser.getFirstname())
                .lastname(tempUser.getLastname())
                .email(tempUser.getEmail())
                .image(
                        ImageBase64.builder()
                        .image(Base64Util.encode(tempImage.getImage()))
                        .name(tempImage.getName())
                        .type(tempImage.getType())
                        .build()
                )
                .build();

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) throws Exception {
        User updatedUser = userService.update(user);
        Image tempImage = updatedUser.getImage();

        UserResponse userResponse = UserResponse
                .builder()
                .firstname(updatedUser.getFirstname())
                .lastname(updatedUser.getLastname())
                .email(updatedUser.getEmail())
                .image(
                        ImageBase64.builder()
                                .image(Base64Util.encode(tempImage.getImage()))
                                .name(tempImage.getName())
                                .type(tempImage.getType())
                                .build()
                )
                .build();

        return ResponseEntity.ok(userResponse);
    }



    private User getAuthenticatedUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

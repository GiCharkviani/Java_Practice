package com.todoList.controllers.user.helpers;

import com.todoList.controllers.auth.helpers.ImageBase64;
import com.todoList.entities.Image;
import com.todoList.entities.User;
import com.todoList.services.image.ImageService;
import com.todoList.utils.AuthenticatedUser;
import com.todoList.utils.Base64Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResponseBuilder {
    private final ImageService imageService;

    public UserResponse build(User user) {
        return actualBuilder(user);
    }

    public UserResponse build() {
        return actualBuilder(AuthenticatedUser.user());
    }

    private UserResponse actualBuilder(User user) {
        Image image = imageService.get(user.getId());

        return UserResponse
                .builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .image(
                        ImageBase64.builder()
                                .image(Base64Util.encode(image.getImage()))
                                .name(image.getName())
                                .type(image.getType())
                                .build()
                )
                .build();
    }
}

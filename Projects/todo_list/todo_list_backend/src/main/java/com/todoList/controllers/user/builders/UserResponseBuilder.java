package com.todoList.controllers.user.builders;

import com.todoList.controllers.auth.DTOs.ImageBase64DTO;
import com.todoList.controllers.user.DTOs.UserResponseDTO;
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

    public UserResponseDTO build(User user) {
        return actualBuilder(user);
    }

    public UserResponseDTO build() {
        return actualBuilder(AuthenticatedUser.user());
    }

    private UserResponseDTO actualBuilder(User user) {
        Image image = imageService.get(user.getId());

        ImageBase64DTO imageBase64DTO = image == null ? null : ImageBase64DTO.builder()
                .image(Base64Util.encode(image.getImage()))
                .name(image.getName())
                .type(image.getType())
                .build();


        return UserResponseDTO
                .builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .image(imageBase64DTO)
                .build();
    }
}

package com.todoList.controllers.user.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.todoList.AOP.customHandlers.deserializers.ImageDeserializer;
import com.todoList.controllers.auth.DTOs.ImageBase64DTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditRequestDTO {
    @NotNull
    @Positive
    private long id;

    @Nullable
    private String firstname;

    @Nullable
    private String lastname;

    @Email
    @Nullable
    private String email;

    @Nullable
    private String password;

    @JsonDeserialize(using = ImageDeserializer.class)
    private ImageBase64DTO image;
}

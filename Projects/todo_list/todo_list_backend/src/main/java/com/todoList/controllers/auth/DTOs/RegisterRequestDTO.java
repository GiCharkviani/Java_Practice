package com.todoList.controllers.auth.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.todoList.AOP.customHandlers.Deserializers.ImageDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @JsonDeserialize(using = ImageDeserializer.class)
    private ImageBase64DTO image;
}

package com.todoList.controllers.auth.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageBase64DTO {
    @NotBlank
    private String image;

    @NotBlank
    private String name;

    @NotBlank
    private String type;
}

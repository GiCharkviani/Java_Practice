package com.todoList.controllers.auth.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageOnRegister {
    private String base64Image;
    private String name;
    private String type;
}

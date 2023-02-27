package com.todoList.controllers.auth.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageBase64 {
    private String image;
    private String name;
    private String type;
}

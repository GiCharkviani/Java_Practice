package com.todoList.controllers.user.helpers;

import com.todoList.controllers.auth.helpers.ImageBase64;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private ImageBase64 image;
}

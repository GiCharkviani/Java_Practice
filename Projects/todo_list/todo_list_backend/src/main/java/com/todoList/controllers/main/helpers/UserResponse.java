package com.todoList.controllers.main.helpers;

import com.todoList.entities.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String image;
    private List<Todo> todos;
}

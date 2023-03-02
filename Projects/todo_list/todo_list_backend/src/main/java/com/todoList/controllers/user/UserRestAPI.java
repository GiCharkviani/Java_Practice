package com.todoList.controllers.user;

import com.todoList.controllers.user.DTOs.UserEditRequestDTO;
import com.todoList.controllers.user.DTOs.UserResponseDTO;
import com.todoList.controllers.user.builders.UserResponseBuilder;
import com.todoList.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserRestAPI {
    private final UserService userService;


    private final UserResponseBuilder userResponseBuilder;

    @GetMapping
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userResponseBuilder.build());
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserEditRequestDTO user) {
        return ResponseEntity.ok(userResponseBuilder.build(userService.update(user)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() throws Exception {
        userService.delete();
        return ResponseEntity.ok().build();
    }

}

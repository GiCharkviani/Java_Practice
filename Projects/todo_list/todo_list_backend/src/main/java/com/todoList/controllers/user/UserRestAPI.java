package com.todoList.controllers.user;

import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.controllers.user.helpers.UserResponse;
import com.todoList.controllers.user.helpers.UserResponseBuilder;
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
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) throws Exception {
        return ResponseEntity.ok(userResponseBuilder.build(userService.update(user)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        userService.delete();
        return ResponseEntity.ok().build();
    }

}

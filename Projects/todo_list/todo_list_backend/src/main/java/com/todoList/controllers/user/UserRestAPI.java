package com.todoList.controllers.user;

import com.todoList.services.jwt.JwtService;
import com.todoList.entities.User;
import com.todoList.services.user.UserService;
import com.todoList.utils.ImageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserRestAPI {

    private final UserService userService;
    private final JwtService jwtService;


    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        String userEmail = jwtService.extractUserEmail(authorizationHeader.substring(7));

        User tempUser = userService.getByEmail(userEmail);

        String image = Base64.getEncoder().encodeToString(ImageUtil.decompressImage(tempUser.getImage().getImage()));

        ResponseUser responseUser = new ResponseUser();
        responseUser.setFirstname(tempUser.getFirstname());
        responseUser.setLastname(tempUser.getLastname());
        responseUser.setEmail(tempUser.getEmail());
        responseUser.setImage(image);

        return ResponseEntity.ok(responseUser);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class ResponseUser {
        private String firstname;
        private String lastname;
        private String email;
        private String image;
    }
}

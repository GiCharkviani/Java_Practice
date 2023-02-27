package com.todoList.controllers.auth;

import com.todoList.controllers.auth.helpers.AuthenticationRequest;
import com.todoList.controllers.auth.helpers.AuthenticationResponse;
import com.todoList.controllers.auth.helpers.RegisterRequest;
import com.todoList.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestAPI {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@ModelAttribute RegisterRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) throws Exception {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}

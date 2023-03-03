package com.todoList.controllers.auth;

import com.todoList.controllers.auth.DTOs.AuthenticationResponseDTO;
import com.todoList.controllers.auth.DTOs.LoginRequestDTO;
import com.todoList.controllers.auth.DTOs.RegisterRequestDTO;
import com.todoList.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestAPI {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register (@Valid @RequestBody RegisterRequestDTO request) throws Exception {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) throws Exception {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}

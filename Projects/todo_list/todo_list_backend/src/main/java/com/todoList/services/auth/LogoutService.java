package com.todoList.services.auth;


import com.todoList.services.token.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LogoutService extends SimpleUrlLogoutSuccessHandler {
    private final TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String jwt = request.getHeader("Authorization").substring(7);
        if(!jwt.isEmpty()) {
            this.tokenService.remove(jwt);
        }
        SecurityContextHolder.clearContext();
        super.onLogoutSuccess(request, response, authentication);
    }
}

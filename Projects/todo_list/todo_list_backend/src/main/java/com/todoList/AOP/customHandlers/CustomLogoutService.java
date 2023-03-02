package com.todoList.AOP.customHandlers;


import com.todoList.AOP.customHandlers.utils.CustomJsonResponseWriter;
import com.todoList.services.token.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutService extends SimpleUrlLogoutSuccessHandler {
    private final TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String jwtWithBearer = request.getHeader("Authorization");
        String readyJwt;
        try {
            readyJwt = jwtWithBearer.substring(7);
        } catch (StringIndexOutOfBoundsException exception) {
            CustomJsonResponseWriter.write(response, "Invalid token", 498);
            return;
        }

        if(!readyJwt.isEmpty()) {
            try {
                this.tokenService.delete(readyJwt);
            } catch (Exception e) {
                CustomJsonResponseWriter.write(response, "Token was not found", HttpStatus.UNAUTHORIZED.value());
            }
        }

        SecurityContextHolder.clearContext();
        super.onLogoutSuccess(request, response, authentication);
    }

}

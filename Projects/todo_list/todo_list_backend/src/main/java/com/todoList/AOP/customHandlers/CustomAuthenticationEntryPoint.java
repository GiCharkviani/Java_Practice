package com.todoList.AOP.customHandlers;

import com.todoList.AOP.customHandlers.responseObjects.CustomBaseResponse;
import com.todoList.utils.ClassToJsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        CustomBaseResponse customBaseResponse = CustomBaseResponse
                .builder()
                .message("Unauthorized")
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        response.getWriter().write(ClassToJsonUtil.toJson(customBaseResponse));
    }
}

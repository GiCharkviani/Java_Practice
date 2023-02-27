package com.todoList.AOP.customHandlers;

import com.todoList.AOP.customHandlers.responseObjects.CustomBaseResponse;
import com.todoList.utils.ClassToJsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        CustomBaseResponse customBaseResponse = CustomBaseResponse
                .builder()
                .message("User is not authorized to access this resource with an explicit deny")
                .status(HttpStatus.FORBIDDEN.value())
                .build();

        response.getWriter().write(ClassToJsonUtil.toJson(customBaseResponse));
    }
}

package com.todoList.AOP.customHandlers;


import com.todoList.AOP.customHandlers.responseObjects.CustomBaseResponse;
import com.todoList.services.token.TokenService;
import com.todoList.utils.ClassToJsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LogoutService extends SimpleUrlLogoutSuccessHandler {
    private final TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String jwtWithBearer = request.getHeader("Authorization");
        String readyJwt;
        try {
            readyJwt = jwtWithBearer.substring(7);
        } catch (StringIndexOutOfBoundsException exception) {
            CustomBaseResponse customBaseResponse = CustomBaseResponse
                    .builder()
                    .message("Token has invalid format")
                    .status(498)
                    .build();

            response.setContentType("application/json");
            response.setStatus(498);
            response.getWriter().write(ClassToJsonUtil.toJson(customBaseResponse));
            return;
        }

        if(!readyJwt.isEmpty()) {
            this.tokenService.remove(readyJwt);
        }

        SecurityContextHolder.clearContext();
        super.onLogoutSuccess(request, response, authentication);
    }
}

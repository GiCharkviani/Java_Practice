package com.todoList.filters;

import com.todoList.configuration.JwtService;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;


/**
 * @TODO
 * 4. Add status to Todos
 * 5. Handle all possible exceptions and send appoperate response
 * 6. Refactor code a bit
 */

@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            if(!request.getRequestURI().contains("/auth/")) {
                try {
                    checkAuth(request, response, filterChain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    private void checkAuth(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws Exception {
        final String authHeader = servletRequest.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        final Token foundToken;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendInvalidResponse(servletResponse);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserEmail(jwt);
        foundToken = tokenService.findTokenByToken(jwt);

        if(userEmail != null) {
            User user = this.userService.getByEmail(userEmail);
            if(user == null) {
                sendInvalidResponse(servletResponse);
                return;
            }
            if(jwtService.isTokenValid(jwt, user) && foundToken != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                sendInvalidResponse(servletResponse);
            }
        }
    }

    private void sendInvalidResponse(HttpServletResponse servletResponse) throws IOException {
        servletResponse.sendError(403, "Unauthorized");
    }
}

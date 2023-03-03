package com.todoList.filters;

import com.todoList.AOP.customHandlers.utils.CustomJsonResponseWriter;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
import com.todoList.utils.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter implements Filter {

    private final UserService userService;
    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;
        final Token foundToken;

        if(authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

       try {
           jwt = authHeader.substring(7);
           userId = JwtUtil.extractUserId(jwt);
           foundToken = tokenService.getByToken(jwt);
       }
       catch (StringIndexOutOfBoundsException e) {
           CustomJsonResponseWriter.write(response, "Invalid token", 498);
           return;
       }
       catch (Exception e) {
           System.out.println("Error while filtering: " + e.getMessage());
           CustomJsonResponseWriter.write(response, "Unauthorized", 401);
           return;
       }


        if(foundToken != null && foundToken.getToken() != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.getById(Long.parseLong(userId));

            if(user != null && JwtUtil.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        Collections.emptyList()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

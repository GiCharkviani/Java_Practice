package com.todoList.filters;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.Token;
import com.todoList.entities.User;
import com.todoList.services.jwt.JwtService;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
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

    private final JwtService jwtService;
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
        final String userEmail;
        final Token foundToken;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

       try {
           jwt = authHeader.substring(7);
           userEmail = jwtService.extractUserEmail(jwt);
           foundToken = tokenService.findTokenByToken(jwt);
       } catch (Exception e) {
           throw new ServletException();
       }


        if(userEmail != null
                && foundToken != null
                && foundToken.getToken() != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user;

            try {
                user = userService.getByEmail(userEmail);
            } catch (UnauthorizedNotFoundException e) {
                filterChain.doFilter(request, response);
                return;
            }

            if(jwtService.isTokenValid(jwt, user)) {
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

package com.todoList.configuration;

import com.todoList.filters.AuthenticationFilter;
import com.todoList.services.token.TokenService;
import com.todoList.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;

   @Bean
   public FilterRegistrationBean<AuthenticationFilter> filterFilterRegistrationBean() {
       FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
       registrationBean.setFilter(authenticationFilter());
       registrationBean.addUrlPatterns("/*");
       return registrationBean;
   }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(jwtService, userService, tokenService);
    }
}

package com.todoList.services.token;

import com.todoList.entities.Token;

public interface TokenService {
    Token findTokenByToken(String token) throws Exception;
    Token save(Token token);
    void remove(String token);
}

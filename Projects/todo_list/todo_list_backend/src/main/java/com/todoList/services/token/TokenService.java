package com.todoList.services.token;

import com.todoList.entities.Token;

import java.util.List;

public interface TokenService {
    List<Token> findAllValidTokenByUser(int userId);
    Token findTokenByToken(String token);
    Token save(Token token);
    void remove(String token);
}

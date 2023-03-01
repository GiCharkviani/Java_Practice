package com.todoList.services.token;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.Token;

import java.util.List;

public interface TokenService {
    Token getByToken(String token) throws UnauthorizedNotFoundException;
    List<Token> getAll();
    Token save(Token token);
    void delete(String token) throws UnauthorizedNotFoundException;
}

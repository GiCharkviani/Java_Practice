package com.todoList.dao.token;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.Token;

public interface TokenDAO {
    Token findTokenByToken(String token) throws UnauthorizedNotFoundException;
    Token save(Token token);
    void remove(String token) throws UnauthorizedNotFoundException;
}

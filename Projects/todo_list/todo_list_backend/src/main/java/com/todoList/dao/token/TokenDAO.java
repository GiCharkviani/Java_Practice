package com.todoList.dao.token;

import com.todoList.entities.Token;

public interface TokenDAO {
    Token findTokenByToken(String token);
    Token save(Token token);
    void remove(String token);
}

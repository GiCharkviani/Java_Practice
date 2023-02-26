package com.todoList.dao.token;

import com.todoList.entities.Token;

import java.util.List;

public interface TokenDAO {
    List<Token> findAllValidTokenByUser(int userId);
    Token findTokenByToken(String token);
    Token save(Token token);
    void remove(String token);
}

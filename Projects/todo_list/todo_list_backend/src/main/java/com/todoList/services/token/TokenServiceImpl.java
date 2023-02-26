package com.todoList.services.token;

import com.todoList.dao.token.TokenDAO;
import com.todoList.entities.Token;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenDAO tokenDAO;

    @Override
    @Transactional
    public List<Token> findAllValidTokenByUser(int userId) {
        return tokenDAO.findAllValidTokenByUser(userId);
    }

    @Override
    @Transactional
    public Token findTokenByToken(String token) {
        return tokenDAO.findTokenByToken(token);
    }

    @Override
    @Transactional
    public Token save(Token token) {
        return tokenDAO.save(token);
    }

    @Override
    @Transactional
    public void remove(String token) {
        tokenDAO.remove(token);
    }
}

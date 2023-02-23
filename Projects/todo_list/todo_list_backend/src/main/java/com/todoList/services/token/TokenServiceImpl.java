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
        return this.tokenDAO.findAllValidTokenByUser(userId);
    }

    @Override
    @Transactional
    public Token findTokenByToken(String token) {
        return this.tokenDAO.findTokenByToken(token);
    }

    @Override
    @Transactional
    public Token save(Token token) {
        return this.tokenDAO.save(token);
    }
}

package com.todoList.services.token;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.dao.token.TokenDAO;
import com.todoList.entities.Token;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenDAO tokenDAO;

    @Override
    @Transactional
    public Token findTokenByToken(String token) throws UnauthorizedNotFoundException {
        return tokenDAO.findTokenByToken(token);
    }

    @Override
    @Transactional
    public Token save(Token token) {
        return tokenDAO.save(token);
    }

    @Override
    @Transactional
    public void remove(String token) throws UnauthorizedNotFoundException {
        tokenDAO.remove(token);
    }
}

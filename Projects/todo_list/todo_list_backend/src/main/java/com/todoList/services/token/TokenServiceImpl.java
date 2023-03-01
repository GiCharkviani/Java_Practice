package com.todoList.services.token;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
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
    public Token getByToken(String token) throws UnauthorizedNotFoundException {
        return tokenDAO.getByToken(token);
    }

    @Override
    public List<Token> getAll() {
        return tokenDAO.getAll();
    }

    @Override
    @Transactional
    public Token save(Token token) {
        return tokenDAO.save(token);
    }

    @Override
    @Transactional
    public void delete(String token) throws UnauthorizedNotFoundException {
        tokenDAO.delete(token);
    }
}

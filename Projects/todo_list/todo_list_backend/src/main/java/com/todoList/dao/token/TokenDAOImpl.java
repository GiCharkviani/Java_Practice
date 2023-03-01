package com.todoList.dao.token;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.Token;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenDAOImpl implements TokenDAO {

    private final EntityManager entityManager;

    @Override
    public Token findTokenByToken(String token) throws UnauthorizedNotFoundException {
        Query theQuery = entityManager.createQuery("FROM Token WHERE token =: actualToken")
                .setParameter("actualToken", token);

        try {
            return (Token)theQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            throw new UnauthorizedNotFoundException("Token was not found");
        }
    }

    @Override
    public Token save(Token token) {
        return entityManager.merge(token);
    }

    @Override
    public void remove(String token) throws UnauthorizedNotFoundException {
        Token foundToken = findTokenByToken(token);
        entityManager.remove(foundToken);
    }
}

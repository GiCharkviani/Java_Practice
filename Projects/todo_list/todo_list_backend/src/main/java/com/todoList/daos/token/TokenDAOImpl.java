package com.todoList.daos.token;

import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.Token;
import com.todoList.utils.AuthenticatedUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TokenDAOImpl implements TokenDAO {

    private final EntityManager entityManager;

    @Override
    public Token getByToken(String token) throws UnauthorizedNotFoundException {
        Query theQuery = entityManager.createQuery("FROM Token WHERE token =: actualToken")
                .setParameter("actualToken", token);

        try {
            return (Token)theQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            throw new UnauthorizedNotFoundException("Token was not found");
        }
    }

    @Override
    public List<Token> getAll() {
        Query theQuery = entityManager.createQuery("FROM Token WHERE user =: user")
                .setParameter("user", AuthenticatedUser.user());

        return theQuery.getResultList();
    }

    @Override
    public Token save(Token token) {
        return entityManager.merge(token);
    }

    @Override
    public void delete(String token) throws UnauthorizedNotFoundException {
        Token foundToken = getByToken(token);
        entityManager.remove(foundToken);
    }
}

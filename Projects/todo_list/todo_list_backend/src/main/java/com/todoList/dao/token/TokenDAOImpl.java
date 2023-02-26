package com.todoList.dao.token;

import com.todoList.entities.Token;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TokenDAOImpl implements TokenDAO {

    private final EntityManager entityManager;

    @Override
    public List<Token> findAllValidTokenByUser(int userId) {
        // @TODO adjust query according to function
        Query theQuery = entityManager.createQuery("FROM Token WHERE user=:userId")
                .setParameter("userId", userId);
        return theQuery.getResultList();
    }

    @Override
    public Token findTokenByToken(String token) {
        String jpql = "SELECT t FROM Token t WHERE t.token =: actualToken";

        TypedQuery<Token> theQuery = entityManager.createQuery(jpql, Token.class)
                .setParameter("actualToken", token);

        List<Token> tokens = theQuery.getResultList();

        if(!tokens.isEmpty()) {
            return tokens.get(0);
        }
        return null;
    }

    @Override
    public Token save(Token token) {
        return entityManager.merge(token);
    }

    @Override
    public void remove(String token) {
        Token foundToken = findTokenByToken(token);
        if(foundToken != null) {
            Query removeQuery = entityManager.createQuery("DELETE FROM Token WHERE id =: removeId")
                    .setParameter("removeId", foundToken.getId());
            removeQuery.executeUpdate();
        }
    }
}

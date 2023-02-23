package com.todoList.dao.user;

import com.todoList.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final EntityManager entityManager;


    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User getByEmail(String email) {
        Query theQuery = entityManager.createQuery("FROM User WHERE email=:userEmail")
                .setParameter("userEmail", email);

        List<User> users = theQuery.getResultList();
        if(!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}

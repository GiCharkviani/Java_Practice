package com.todoList.dao.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final EntityManager entityManager;

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User getByEmail(String email) throws UnauthorizedNotFoundException {
        Query theQuery = entityManager.createQuery("FROM User WHERE email=:userEmail")
                .setParameter("userEmail", email);

        try {
            return (User) theQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            throw new UnauthorizedNotFoundException("Invalid email or password");
        }
    }

    @Override
    public User getById(long id) {
        User user = entityManager.find(User.class, id);
        if(user == null)
            throw new NotFoundException("User not found. ID: " + id);
        return user;
    }

    @Override
    public Boolean checkIfEmailExists(String email) {
        return entityManager
                .createQuery("SELECT EXISTS(SELECT 1 FROM User WHERE email =: providedEmail)", Boolean.class)
                .setParameter("providedEmail", email)
                .getSingleResult();
    }

    @Override
    public void delete(long id) throws NotFoundException {
        User user = getById(id);
        user.setTodos(null);
        user.setTokens(null);

        entityManager.remove(user);
        entityManager.flush();
    }
}

package com.todoList.dao.user;

import com.todoList.AOP.Exceptions.ExceptionObjects.DuplicatedEmailException;
import com.todoList.AOP.Exceptions.ExceptionObjects.UnauthorizedNotFoundException;
import com.todoList.controllers.user.helpers.UserRequest;
import com.todoList.entities.User;
import com.todoList.utils.AuthenticatedUser;
import com.todoList.utils.Base64Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final EntityManager entityManager;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User getByEmail(String email) throws UnauthorizedNotFoundException {
        Query theQuery = entityManager.createQuery("FROM User WHERE email=:userEmail")
                .setParameter("userEmail", email);

        try {
            return (User) theQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            throw new UnauthorizedNotFoundException("User was not found");
        }
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User update(UserRequest userRequest) throws Exception {
        boolean userExists = checkIfEmailExists(userRequest.getEmail());

        if(userExists)
            throw new DuplicatedEmailException(userRequest.getEmail());

        User user = getById(userRequest.getId());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return save(user);
    }

    @Override
    public Boolean checkIfEmailExists(String email) {
        return entityManager
                .createQuery("SELECT EXISTS(SELECT 1 FROM User WHERE email =: providedEmail)", Boolean.class)
                .setParameter("providedEmail", email)
                .getSingleResult();
    }

    @Override
    public void delete(int id) {
        User user = getById(id);
        user.setTodos(null);
        user.setTokens(null);

        entityManager.remove(user);
        entityManager.flush();
    }
}

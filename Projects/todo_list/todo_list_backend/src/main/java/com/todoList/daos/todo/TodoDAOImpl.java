package com.todoList.daos.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Todo;
import com.todoList.utils.AuthenticatedUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TodoDAOImpl implements TodoDAO {
    private final EntityManager entityManager;

    @Override
    public List<Todo> getAllLimited(int from, int to) {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user ORDER BY whenTodo DESC")
                .setParameter("user", AuthenticatedUser.user());
        theQuery.setFirstResult(from - 1);
        theQuery.setMaxResults(to - from + 1);
        return theQuery.getResultList();
    }

    @Override
    public List<Todo> getAll() {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user ORDER BY whenTodo DESC")
                .setParameter("user", AuthenticatedUser.user());
        return theQuery.getResultList();
    }

    @Override
    public Todo get(long id) throws NotFoundException {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user AND id=:todoId")
                .setParameter("user", AuthenticatedUser.user())
                .setParameter("todoId", id);

        try {
            return (Todo) theQuery.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            throw new NotFoundException("ToDo was not found. Id: " + id);
        }
    }

    @Override
    public Todo save(Todo todo) {
        return entityManager.merge(todo);
    }


    @Override
    public Todo delete(long id) throws NotFoundException {
        Todo todo = get(id);
        this.entityManager.remove(todo);
        return todo;
    }
}
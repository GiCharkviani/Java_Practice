package com.todoList.daos.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.controllers.todo.DTOs.TodoQueryParamDTO;
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
    public List<Todo> getAllLimited(TodoQueryParamDTO todoQueryParamDTO) {
        return getAllLimitedQuery(todoQueryParamDTO).getResultList();
    }

    private Query getAllLimitedQuery(TodoQueryParamDTO todoQueryParamDTO) {
        String jpql = "SELECT t FROM Todo t WHERE t.user =:user";

        if(todoQueryParamDTO.getTodo() != null && !todoQueryParamDTO.getTodo().contains(" ")) {
            jpql += " AND t.whatTodo LIKE " + "'%" + todoQueryParamDTO.getTodo() + "%'";
        }

        if(todoQueryParamDTO.getStatus() != null) {
            jpql += " AND t.status = " + todoQueryParamDTO.getStatus();
        }

        if(todoQueryParamDTO.getPriority() != null) {
            jpql += " AND t.priority = " + todoQueryParamDTO.getPriority();
        }

        if(todoQueryParamDTO.getSortBy() != null) {
            jpql += " ORDER BY t." + todoQueryParamDTO.getSortBy().name();
            if(todoQueryParamDTO.getOrder() != null) {
                jpql += " " + todoQueryParamDTO.getOrder().name();
            }
        }

        return entityManager.createQuery(jpql)
                .setParameter("user", AuthenticatedUser.user())
                .setFirstResult(todoQueryParamDTO.getFrom() - 1)
                .setMaxResults(todoQueryParamDTO.getTo() - todoQueryParamDTO.getFrom() + 1);
    }

    @Override
    public List<Todo> getAll() {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user")
                .setParameter("user", AuthenticatedUser.user());
        return theQuery.getResultList();
    }

    @Override
    public long getTotalCount() {
        return entityManager.createQuery("SELECT COUNT(t) FROM Todo t WHERE t.user =: user", Long.class)
                .setParameter("user", AuthenticatedUser.user())
                .getSingleResult();
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

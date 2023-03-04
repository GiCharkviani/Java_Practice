package com.todoList.daos.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.OrderBy;
import com.todoList.enums.todo.SortBy;
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
    public List<Todo> getAllLimited(String todo, int from, int to, SortBy sortBy, OrderBy orderBy) {
        Query theQuery = entityManager
                .createQuery("FROM Todo t WHERE t.user=:user AND t.whatTodo LIKE :pattern ORDER BY " +
                        "CASE WHEN :sortBy = 'whatTodo' AND :orderBy = 'asc' THEN t.whatTodo END ASC," +
                        "CASE WHEN :sortBy = 'whatTodo' AND :orderBy = 'desc' THEN t.whatTodo END DESC," +
                        "CASE WHEN :sortBy = 'whenTodo' AND :orderBy = 'asc' THEN t.whenTodo END ASC," +
                        "CASE WHEN :sortBy = 'whenTodo' AND :orderBy = 'desc' THEN t.whenTodo END DESC," +
                        "CASE WHEN :sortBy = 'status' AND :orderBy = 'asc' THEN t.status END ASC," +
                        "CASE WHEN :sortBy = 'status' AND :orderBy = 'desc' THEN t.status END DESC," +
                        "CASE WHEN :sortBy = 'createdAt' AND :orderBy = 'asc' THEN t.createdAt END ASC," +
                        "CASE WHEN :sortBy = 'createdAt' AND :orderBy = 'desc' THEN t.createdAt END DESC," +
                        "CASE WHEN :sortBy = 'lastModifiedAt' AND :orderBy = 'asc' THEN t.lastModifiedAt END ASC," +
                        "CASE WHEN :sortBy = 'lastModifiedAt' AND :orderBy = 'desc' THEN t.lastModifiedAt END DESC")

                .setParameter("user", AuthenticatedUser.user())
                .setParameter("pattern", "%" + todo + "%")
                .setParameter("sortBy", sortBy.name())
                .setParameter("orderBy", orderBy.name())
                .setFirstResult(from - 1)
                .setMaxResults(to - from + 1);
        return theQuery.getResultList();
    }

    @Override
    public List<Todo> getAll() {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user")
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

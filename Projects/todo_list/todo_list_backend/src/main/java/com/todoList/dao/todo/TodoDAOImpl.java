package com.todoList.dao.todo;

import com.todoList.entities.Todo;
import com.todoList.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TodoDAOImpl implements TodoDAO {

    private final EntityManager entityManager;


    @Override
    public List<Todo> getAll(User user) {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user ORDER BY whenTodo DESC")
                .setParameter("user", user);
        return theQuery.getResultList();
    }

    @Override
    public Todo findById(int id, User user) {
        Query theQuery = entityManager.createQuery("FROM Todo WHERE user=:user AND id=:todoId")
                .setParameter("user", user)
                .setParameter("todoId", id);

        List<Todo> todos = theQuery.getResultList();

        return todos.isEmpty() ? null : todos.get(0);
    }

    @Override
    public Todo save(Todo todo) {
        Todo foundTodo = entityManager.find(Todo.class, todo.getId());
        if (foundTodo != null) {
            throw new RuntimeException();
        }
        return entityManager.merge(todo);
    }

    @Override
    public Todo update(int id, Todo todo, User user) {
        Todo tempTodo = findById(id, user);
        if(tempTodo == null) {
            return null;
        }
        tempTodo.setWhatTodo(todo.getWhatTodo());
        tempTodo.setWhenTodo(todo.getWhenTodo());

        return entityManager.merge(tempTodo);
    }


    @Override
    public void deleteById(long id, User user) {
        Query theQuery = entityManager.createQuery("DELETE FROM Todo WHERE id=:todoId AND user=:user")
                .setParameter("todoId", id)
                .setParameter("user", user);

        theQuery.executeUpdate();
    }
}

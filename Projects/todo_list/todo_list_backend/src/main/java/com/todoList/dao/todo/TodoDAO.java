package com.todoList.dao.todo;

import com.todoList.entities.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TodoDAO implements ITodoDAO {

    private final EntityManager entityManager;


    @Override
    public List<Todo> getAll() {
        Query theQuery = entityManager.createQuery("FROM Todo ORDER BY whenTodo desc");
        return theQuery.getResultList();
    }

    @Override
    public Todo findById(long id) {
        return entityManager.find(Todo.class, id);
    }

    @Override
    public void save(Todo todo_value) {
        Todo dbTodo = entityManager.merge(todo_value);
    }

    @Override
    public void deleteById(long id) {
        Query theQuery = entityManager.createQuery("delete from Todo where id=:todoId")
                .setParameter("todoId", id);
        theQuery.executeUpdate();
    }
}

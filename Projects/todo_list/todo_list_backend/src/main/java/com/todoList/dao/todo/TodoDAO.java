package com.todoList.dao.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoDAO {
    List<Todo> getAllLimited(int from, int to);
    List<Todo> getAll();
    Todo get(long id) throws NotFoundException;
    Todo save(Todo todo);
    Todo update(Todo todo) throws NotFoundException;
    void updateStatus(long id, Status status);
    Todo delete(long id) throws NotFoundException;
}

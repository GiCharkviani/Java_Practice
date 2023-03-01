package com.todoList.dao.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoDAO {
    List<Todo> getAll(int from, int to);
    Todo get(int id) throws NotFoundException;
    Todo save(Todo todo);
    Todo update(Todo todo) throws NotFoundException;
    void updateStatus(int id, Status status);
    Todo delete(int id) throws NotFoundException;
}

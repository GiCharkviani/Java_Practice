package com.todoList.dao.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Todo;

import java.util.List;

public interface TodoDAO {
    List<Todo> getAllLimited(int from, int to);
    List<Todo> getAll();
    Todo get(long id) throws NotFoundException;
    Todo save(Todo todo);
    Todo delete(long id) throws NotFoundException;
}

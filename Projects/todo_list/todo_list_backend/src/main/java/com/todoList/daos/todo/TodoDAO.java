package com.todoList.daos.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.controllers.todo.DTOs.TodoQueryParamDTO;
import com.todoList.entities.Todo;

import java.util.List;

public interface TodoDAO {
    List<Todo> getAllLimited(TodoQueryParamDTO todoQueryParamDTO);
    List<Todo> getAll();
    long getTotalCount();
    Todo get(long id) throws NotFoundException;
    Todo save(Todo todo);
    Todo delete(long id) throws NotFoundException;
}

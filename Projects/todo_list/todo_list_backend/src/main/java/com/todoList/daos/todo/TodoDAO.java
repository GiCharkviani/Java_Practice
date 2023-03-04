package com.todoList.daos.todo;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.OrderBy;
import com.todoList.enums.todo.SortBy;

import java.util.List;

public interface TodoDAO {
    List<Todo> getAllLimited(String todo, int from, int to, SortBy sortBy, OrderBy orderBy);
    List<Todo> getAll();
    Todo get(long id) throws NotFoundException;
    Todo save(Todo todo);
    Todo delete(long id) throws NotFoundException;
}

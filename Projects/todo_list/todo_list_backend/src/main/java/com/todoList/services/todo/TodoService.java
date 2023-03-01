package com.todoList.services.todo;

import com.todoList.controllers.todo.helpers.TodoRequest;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoService {
    List<Todo> getAllLimited(int from, int to);
    List<Todo> getAll();
    Todo get(long id);
    Todo save(TodoRequest todo);
    Todo update(Todo todo);
    void updateStatus(long id, Status status);
    Todo delete(long id);
}

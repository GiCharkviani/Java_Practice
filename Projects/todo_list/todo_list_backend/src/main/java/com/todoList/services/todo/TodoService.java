package com.todoList.services.todo;

import com.todoList.controllers.todo.helpers.TodoRequest;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoService {
    List<Todo> getAll(int from, int to);
    Todo get(int id);
    Todo save(TodoRequest todo);
    Todo update(Todo todo);
    void updateStatus(int id, Status status);
    Todo delete(int id);
}

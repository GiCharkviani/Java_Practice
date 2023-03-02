package com.todoList.services.todo;

import com.todoList.controllers.todo.DTOs.TodoAddRequestDTO;
import com.todoList.controllers.todo.DTOs.TodoEditRequestDTO;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoService {
    List<Todo> getAllLimited(int from, int to);
    List<Todo> getAll();
    Todo get(long id);
    Todo save(TodoAddRequestDTO todo);
    Todo update(TodoEditRequestDTO todo);
    void updateStatus(long id, Status status);
    Todo delete(long id);
}

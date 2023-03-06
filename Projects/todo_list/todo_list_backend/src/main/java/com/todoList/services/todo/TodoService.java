package com.todoList.services.todo;

import com.todoList.controllers.todo.DTOs.TodoAddRequestDTO;
import com.todoList.controllers.todo.DTOs.TodoEditRequestDTO;
import com.todoList.controllers.todo.DTOs.TodoResponseDTO;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.OrderBy;
import com.todoList.enums.todo.Priority;
import com.todoList.enums.todo.SortBy;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoService {
    TodoResponseDTO getAllLimited(String todo, int from, int to, SortBy sortBy, OrderBy orderBy);
    List<Todo> getAll();
    Todo get(long id);
    Todo save(TodoAddRequestDTO todo);
    Todo update(TodoEditRequestDTO todo);
    void updateStatus(long id, Status status);
    void updatePriority(long id, Priority priority);
    Todo delete(long id);
}

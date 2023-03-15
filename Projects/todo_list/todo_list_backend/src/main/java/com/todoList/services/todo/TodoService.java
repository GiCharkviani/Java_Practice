package com.todoList.services.todo;

import com.todoList.controllers.todo.DTOs.TodoAddRequestDTO;
import com.todoList.controllers.todo.DTOs.TodoEditRequestDTO;
import com.todoList.controllers.todo.DTOs.TodoQueryParamDTO;
import com.todoList.controllers.todo.DTOs.TodoResponseDTO;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Priority;
import com.todoList.enums.todo.Status;

import java.util.List;

public interface TodoService {
    TodoResponseDTO getAllLimited(TodoQueryParamDTO todoQueryParamDTO);
    List<Todo> getAll();
    Todo get(long id);
    Todo save(TodoAddRequestDTO todo);
    Todo update(TodoEditRequestDTO todo);
    void updateStatus(long id, Status status);
    void updatePriority(long id, Priority priority);
    Todo delete(long id);
}

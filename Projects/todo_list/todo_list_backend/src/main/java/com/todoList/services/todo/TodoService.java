package com.todoList.services.todo;

import com.todoList.entities.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getAll(int from, int to);
    Todo findById(int id);
    Todo save(Todo todo);
    Todo update(Todo todo);
    void deleteById(long id);
}

package com.todoList.services.todo;

import com.todoList.entities.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getAll(String email);
    Todo findById(int id, String email);
    Todo save(Todo todo, String email);
    Todo update(int id, Todo todo, String email);
    void deleteById(long id, String email);
}

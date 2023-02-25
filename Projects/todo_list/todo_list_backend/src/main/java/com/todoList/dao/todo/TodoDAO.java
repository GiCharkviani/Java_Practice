package com.todoList.dao.todo;

import com.todoList.entities.Todo;
import com.todoList.entities.User;

import java.util.List;

public interface TodoDAO {
    List<Todo> getAll(User user, int from, int to);
    Todo findById(int id, User user);
    Todo save(Todo todo);
    Todo update(int id, Todo todo, User user);
    void deleteById(long id, User user);
}

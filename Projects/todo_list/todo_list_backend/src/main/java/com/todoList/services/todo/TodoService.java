package com.todoList.services.todo;

import com.todoList.dao.todo.TodoDAO;
import com.todoList.entities.Todo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService implements ITodoService {

    private final TodoDAO todoDAO;

    @Override
    @Transactional
    public List<Todo> getAll() {
        return todoDAO.getAll();
    }

    @Override
    @Transactional
    public Todo findById(long id) {
        return todoDAO.findById(id);
    }

    @Override
    @Transactional
    public void save(Todo todo_value) {
        todoDAO.save(todo_value);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        todoDAO.deleteById(id);
    }
}

package com.todoList.services.todo;

import com.todoList.controllers.todo.helpers.TodoRequest;
import com.todoList.dao.todo.TodoDAO;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoDAO todoDAO;


    @Override
    @Transactional
    public List<Todo> getAllLimited(int from, int to) {
        return todoDAO.getAllLimited(from, to);
    }

    @Override
    public List<Todo> getAll() {
        return todoDAO.getAll();
    }

    @Override
    @Transactional
    public Todo get(long id) {
        return todoDAO.get(id);
    }

    @Override
    @Transactional
    public Todo save(TodoRequest todoRequest) {
        LocalDateTime localDateTime = LocalDateTime.
                ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());

        Todo todo = Todo
                .builder()
                .whatTodo(todoRequest.getWhatTodo())
                .whenTodo(todoRequest.getWhenTodo())
                .status(todoRequest.getStatus())
                .createdAt(localDateTime)
                .lastModifiedAt(localDateTime)
                .build();

        return todoDAO.save(todo);
    }

    @Override
    @Transactional
    public Todo update(Todo todo) {
        return todoDAO.update(todo);
    }

    @Override
    @Transactional
    public void updateStatus(long id, Status status) {
        todoDAO.updateStatus(id, status);
    }

    @Override
    @Transactional
    public Todo delete(long id) {
       return todoDAO.delete(id);
    }

}

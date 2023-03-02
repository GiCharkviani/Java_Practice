package com.todoList.services.todo;

import com.todoList.controllers.todo.DTOs.TodoAddRequestDTO;
import com.todoList.controllers.todo.DTOs.TodoEditRequestDTO;
import com.todoList.daos.todo.TodoDAO;
import com.todoList.entities.Todo;
import com.todoList.enums.todo.Status;
import com.todoList.utils.AuthenticatedUser;
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
    public Todo save(TodoAddRequestDTO todoRequest) {
        LocalDateTime localDateTime = LocalDateTime.
                ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());

        Todo todo = Todo
                .builder()
                .whatTodo(todoRequest.getWhatTodo())
                .whenTodo(todoRequest.getWhenTodo())
                .status(todoRequest.getStatus())
                .user(AuthenticatedUser.user())
                .createdAt(localDateTime)
                .lastModifiedAt(localDateTime)
                .build();

        return todoDAO.save(todo);
    }

    @Override
    @Transactional
    public Todo update(TodoEditRequestDTO todo) {
        Todo tempTodo = get(todo.getId());

        LocalDateTime localDateTime = LocalDateTime.
                ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());

        tempTodo.setWhatTodo(todo.getWhatTodo());
        tempTodo.setWhenTodo(todo.getWhenTodo());
        tempTodo.setStatus(todo.getStatus());
        tempTodo.setLastModifiedAt(localDateTime);

        return todoDAO.save(tempTodo);
    }

    @Override
    @Transactional
    public void updateStatus(long id, Status status) {
        Todo tempTodo = get(id);
        tempTodo.setStatus(status);
        todoDAO.save(tempTodo);
    }

    @Override
    @Transactional
    public Todo delete(long id) {
       return todoDAO.delete(id);
    }

}

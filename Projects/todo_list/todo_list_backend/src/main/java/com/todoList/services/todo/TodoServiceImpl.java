package com.todoList.services.todo;

import com.todoList.dao.todo.TodoDAOImpl;
import com.todoList.entities.Todo;
import com.todoList.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoDAOImpl todoDAO;


    @Override
    @Transactional
    public List<Todo> getAll(int from, int to) {
        return todoDAO.getAll(getAuthenticatedUser(), from, to);
    }

    @Override
    @Transactional
    public Todo findById(int id) {
        return todoDAO.findById(id, getAuthenticatedUser());
    }

    @Override
    @Transactional
    public Todo save(Todo todo) {
        todo.setUser(getAuthenticatedUser());
        return todoDAO.save(todo);
    }

    @Override
    @Transactional
    public Todo update(Todo todo) {
        return todoDAO.update(todo, getAuthenticatedUser());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        todoDAO.deleteById(id, getAuthenticatedUser());
    }

    private User getAuthenticatedUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

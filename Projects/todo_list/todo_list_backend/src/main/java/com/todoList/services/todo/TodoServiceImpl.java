package com.todoList.services.todo;

import com.todoList.dao.todo.TodoDAOImpl;
import com.todoList.entities.Todo;
import com.todoList.entities.User;
import com.todoList.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoDAOImpl todoDAO;
    private final UserService userService;


    @Override
    @Transactional
    public List<Todo> getAll(String email) {
        User user = userService.getByEmail(email);
        return todoDAO.getAll(user);
    }

    @Override
    @Transactional
    public Todo findById(int id, String email) {
        User user = userService.getByEmail(email);
        return todoDAO.findById(id, user);
    }

    @Override
    @Transactional
    public Todo save(Todo todo, String email) {
        User user = userService.getByEmail(email);
        todo.setUser(user);
        return todoDAO.save(todo);
    }

    @Override
    public Todo update(int id, Todo todo) {
        return todoDAO.update(id, todo);
    }

    @Override
    @Transactional
    public void deleteById(long id, String email) {
        User user = userService.getByEmail(email);
        todoDAO.deleteById(id, user);
    }
}

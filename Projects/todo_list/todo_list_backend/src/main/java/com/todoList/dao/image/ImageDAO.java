package com.todoList.dao.image;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Image;

public interface ImageDAO {
    Image get(long id) throws NotFoundException;
    Image save(Image image);
    void delete(long id) throws NotFoundException;
}

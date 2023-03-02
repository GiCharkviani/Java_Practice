package com.todoList.services.image;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.entities.Image;

public interface ImageService {
    Image get(long id) throws NotFoundException;
    Image uploadImage(Image image);
    void delete(long id) throws NotFoundException;
}

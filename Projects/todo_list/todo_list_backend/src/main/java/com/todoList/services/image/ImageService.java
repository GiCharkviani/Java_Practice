package com.todoList.services.image;

import com.todoList.entities.Image;

public interface ImageService {
    Image get(long id);
    Image uploadImage(Image image);
    void delete(long id);
}

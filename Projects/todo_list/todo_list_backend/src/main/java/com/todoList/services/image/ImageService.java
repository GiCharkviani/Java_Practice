package com.todoList.services.image;

import com.todoList.entities.Image;

public interface ImageService {
    Image get(int id);
    Image uploadImage(Image image);
    void delete(int id);
}

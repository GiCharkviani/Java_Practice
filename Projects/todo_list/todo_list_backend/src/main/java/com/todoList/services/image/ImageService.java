package com.todoList.services.image;

import com.todoList.entities.Image;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(byte[] image, String name, String type) throws IOException;
    void remove(int id);
}

package com.todoList.dao.image;

import com.todoList.entities.Image;

public interface ImageDAO {
    Image get(int id);
    Image save(Image image);
    void delete(int id);
}

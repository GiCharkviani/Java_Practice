package com.todoList.dao.image;

import com.todoList.entities.Image;

public interface ImageDAO {
    Image get(long id);
    Image save(Image image);
    void delete(long id);
}

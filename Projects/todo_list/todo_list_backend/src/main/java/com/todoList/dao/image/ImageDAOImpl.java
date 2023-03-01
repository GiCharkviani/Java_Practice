package com.todoList.dao.image;

import com.todoList.entities.Image;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ImageDAOImpl implements ImageDAO {

    private EntityManager entityManager;

    @Override
    public Image get(int id) {
        return entityManager.find(Image.class, id);
    }

    @Override
    public Image save(Image image) {
        return entityManager.merge(image);
    }

    @Override
    public void delete(int id) {
        Image image = get(id);
        image.setUser(null);
        entityManager.remove(image);
    }

}

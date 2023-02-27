package com.todoList.dao.image;

import com.todoList.entities.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ImageDAOImpl implements ImageDAO {

    private EntityManager entityManager;

    @Override
    public Image save(Image image) {
        return entityManager.merge(image);
    }

    @Override
    public void remove(int id) {
        Query removeQuery = entityManager.createQuery("DELETE FROM Image WHERE id =: imageId")
                .setParameter("imageId", id);
        removeQuery.executeUpdate();
    }


}

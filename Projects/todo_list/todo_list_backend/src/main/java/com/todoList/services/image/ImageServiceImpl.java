package com.todoList.services.image;

import com.todoList.AOP.Exceptions.ExceptionObjects.NotFoundException;
import com.todoList.daos.image.ImageDAO;
import com.todoList.entities.Image;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private ImageDAO imageDAO;

    @Override
    @Transactional
    public Image get(long id) throws NotFoundException {
        return imageDAO.get(id);
    }

    @Override
    @Transactional
    public Image uploadImage(Image image) {
        return imageDAO.save(image);
    }

    @Override
    @Transactional
    public void delete(long id) throws NotFoundException {
        imageDAO.delete(id);
    }
}

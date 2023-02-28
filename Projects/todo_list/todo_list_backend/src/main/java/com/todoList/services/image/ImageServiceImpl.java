package com.todoList.services.image;

import com.todoList.dao.image.ImageDAO;
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
    public Image get(int id) {
        return imageDAO.get(id);
    }

    @Override
    @Transactional
    public Image uploadImage(Image image) {
        return imageDAO.save(image);

    }

    @Override
    @Transactional
    public void remove(int id) {
        imageDAO.remove(id);
    }
}

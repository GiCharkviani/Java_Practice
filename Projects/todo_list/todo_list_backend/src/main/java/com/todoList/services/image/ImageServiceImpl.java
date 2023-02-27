package com.todoList.services.image;

import com.todoList.dao.image.ImageDAO;
import com.todoList.entities.Image;
import com.todoList.utils.ImageUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private ImageDAO imageDAO;

    @Override
    @Transactional
    public Image uploadImage(byte[] file, String name, String type) throws IOException {
        return imageDAO
                .save(Image.builder()
                        .name(name)
                        .type(type)
                        .image(ImageUtil.compressImage(file)).build());

    }

    @Override
    public void remove(int id) {
        imageDAO.remove(id);
    }
}

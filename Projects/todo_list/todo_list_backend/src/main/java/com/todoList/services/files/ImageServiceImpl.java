package com.todoList.services.files;

import com.todoList.dao.files.ImageDAO;
import com.todoList.entities.Image;
import com.todoList.utils.ImageUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private ImageDAO imageRepository;

    @Override
    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException {
        return imageRepository
                .save(Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .image(ImageUtil.compressImage(file.getBytes())).build());

    }
}

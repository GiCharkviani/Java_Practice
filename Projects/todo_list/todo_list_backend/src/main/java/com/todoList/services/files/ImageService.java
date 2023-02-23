package com.todoList.services.files;

import com.todoList.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile image) throws IOException;
}

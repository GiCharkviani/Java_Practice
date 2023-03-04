package com.todoList.utils;

import com.todoList.controllers.auth.DTOs.ImageBase64DTO;
import com.todoList.entities.Image;
import com.todoList.entities.User;
import jakarta.validation.constraints.NotNull;

import java.util.Base64;
import java.util.regex.Pattern;

public class Base64Util {
    public static final Pattern BASE_64_IMAGE_PATTERN =
            Pattern.compile("^data:image/([a-zA-Z]*);base64,([\\\\S]*)$");
    public static String encode(byte[] file) {
        return Base64.getEncoder().encodeToString(ImageUtil.decompressImage(file));
    }

    public static byte[] decode(@NotNull String base64File) {
        String readyBase64File = base64File.substring(0, 50).contains("base64") ?
                base64File.split(",")[1] :
                base64File;

        return Base64.getDecoder().decode(readyBase64File);
    }

    public static Image imageEntity(ImageBase64DTO imageBase64, User user) {
        byte[] imageBytes = decode(imageBase64.getImage());
        return Image.builder()
                .name(imageBase64.getName())
                .type(imageBase64.getType())
                .user(user)
                .image(ImageUtil.compressImage(imageBytes))
                .build();
    }
}

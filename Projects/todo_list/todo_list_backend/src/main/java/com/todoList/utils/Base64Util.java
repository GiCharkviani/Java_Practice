package com.todoList.utils;

import jakarta.validation.constraints.NotNull;

import java.util.Base64;

public class Base64Util {
    public static String encode(byte[] file) {
        return Base64.getEncoder().encodeToString(ImageUtil.decompressImage(file));
    }

    public static byte[] decode(@NotNull String base64File) {
        String readyBase64File = base64File.substring(0, 50).contains("base64") ?
                base64File.split(",")[1] :
                base64File;

        return Base64.getDecoder().decode(readyBase64File);
    }
}

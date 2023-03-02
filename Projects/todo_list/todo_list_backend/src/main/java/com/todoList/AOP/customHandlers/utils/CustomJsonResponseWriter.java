package com.todoList.AOP.customHandlers.utils;

import com.todoList.AOP.customHandlers.responseObjects.CustomBaseResponse;
import com.todoList.utils.ClassToJsonUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomJsonResponseWriter {

    public static void write(HttpServletResponse response, String text, int status) throws IOException {
        CustomBaseResponse customBaseResponse = CustomBaseResponse
                .builder()
                .message(text)
                .status(status)
                .build();

        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(ClassToJsonUtil.toJson(customBaseResponse));
    }

}

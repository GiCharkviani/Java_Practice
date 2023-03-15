package com.todoList.AOP.customHandlers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.todoList.controllers.auth.DTOs.ImageBase64DTO;
import com.todoList.utils.Base64Util;

import java.io.IOException;

public class ImageDeserializer extends JsonDeserializer<ImageBase64DTO> {
    @Override
    public ImageBase64DTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
       JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
       boolean isBase64Image = Base64Util.BASE_64_IMAGE_PATTERN.matcher(jsonNode.get("image").textValue()).matches();

       if(jsonNode.isEmpty() || !isBase64Image)
           return null;
       return ImageBase64DTO
               .builder()
               .image(jsonNode.get("image").textValue())
               .name(jsonNode.get("name").textValue())
               .type(jsonNode.get("type").textValue())
               .build();
    }
}

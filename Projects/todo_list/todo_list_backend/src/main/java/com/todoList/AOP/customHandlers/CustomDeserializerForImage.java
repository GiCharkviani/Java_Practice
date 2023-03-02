package com.todoList.AOP.customHandlers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.todoList.controllers.auth.DTOs.ImageBase64DTO;

import java.io.IOException;

public class CustomDeserializerForImage extends JsonDeserializer<ImageBase64DTO> {
    @Override
    public ImageBase64DTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
       JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
       if(jsonNode.isEmpty())
           return null;
       return ImageBase64DTO
               .builder()
               .image(jsonNode.get("image").textValue())
               .name(jsonNode.get("name").textValue())
               .type(jsonNode.get("type").textValue())
               .build();
    }
}

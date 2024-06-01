package com.aluracursos.literalura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    @Autowired
    private ObjectMapper mapper;

    public <T> T deserialize(String json, Class<T> clase) {

        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON content is null or empty");
        }

        try {
            return mapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON to " + clase.getName(), e);
        }
    }
}

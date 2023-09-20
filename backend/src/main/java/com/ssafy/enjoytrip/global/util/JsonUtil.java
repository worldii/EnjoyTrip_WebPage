package com.ssafy.enjoytrip.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static Object readValue(String json,
        Class<?> boardSaveRequestClass) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Object request = objectMapper.readValue(json, boardSaveRequestClass);
            return request;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

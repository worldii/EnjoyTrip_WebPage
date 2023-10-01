package com.ssafy.enjoytrip.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.global.error.EnjoyTripException;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object readValue(
        final String json,
        final Class<?> request
    ) {
        try {
            return objectMapper.readValue(json, request);
        } catch (JsonProcessingException e) {
            throw new EnjoyTripException(e);
        }
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new EnjoyTripException(e);
        }
    }
}

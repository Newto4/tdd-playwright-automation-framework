package org.fast.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonDataReader {

    /**
     * This is dynamic JSON data loader utility
     * @param className - for which test user wants to load the data
     * @return Map<String,String> : data key and value
     */
    public static Map<String, Object> loadTestData(Class<?> className) {
        String fileName = className.getSimpleName() + ".json";
        String path = "src/test/resources/testdata/" + fileName;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data from: " + path, e);
        }
    }
}

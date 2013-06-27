package net.mademocratie.gae.server.services.helper;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonHelper {
    public static String convertIntoJson(Object myObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(myObject);
    }
    public static <T> T readFromJson(String jsonData, java.lang.Class<T> tClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonData, tClass);
    }
}

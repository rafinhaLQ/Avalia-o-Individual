package uol.compass.ms.order.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtils {
    public static ObjectWriter mapper;

    static {
        mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer().withDefaultPrettyPrinter();
    }

    public static String mapToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}

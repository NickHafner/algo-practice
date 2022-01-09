package common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;

import java.io.IOException;
import java.util.List;

public class LoadJson<T> {
    public static <T> T loadResource(String resourceName, TypeReference typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        val is = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        return mapper.readValue(is, typeReference);

    }
}

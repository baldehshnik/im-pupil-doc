package im.pupil.api.worker.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.pupil.api.exception.parsing.DataParsingException;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    public <T> T parseJsonToDto(String jsonDto, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonDto, clazz);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            throw new DataParsingException();
        }
    }

}

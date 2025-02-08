package im.pupil.api.domain.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.pupil.api.domain.exception.parsing.DataParsingException;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {

    public <T> T parseJsonToDto(String jsonDto, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonDto, clazz);
        } catch (JsonProcessingException e) {
            throw new DataParsingException();
        }
    }
}

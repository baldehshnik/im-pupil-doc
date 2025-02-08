package im.pupil.api.domain.exception.parsing;

public class DataParsingException extends RuntimeException {
    public DataParsingException() {
        super("Data parsing failed");
    }
}

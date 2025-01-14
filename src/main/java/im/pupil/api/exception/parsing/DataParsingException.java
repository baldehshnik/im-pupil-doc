package im.pupil.api.exception.parsing;

public class DataParsingException extends RuntimeException {
    public DataParsingException() {
        super("Data parsing failed");
    }
}

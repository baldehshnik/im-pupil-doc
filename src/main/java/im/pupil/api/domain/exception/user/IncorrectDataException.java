package im.pupil.api.domain.exception.user;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException() {
        super("Incorrect data");
    }
}

package im.pupil.api.exception.user;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException() {
        super("Incorrect data");
    }
}

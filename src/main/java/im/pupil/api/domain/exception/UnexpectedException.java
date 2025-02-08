package im.pupil.api.domain.exception;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException() { super("Unexpected server exception"); }
    public UnexpectedException(String message) {
        super(message);
    }
}
























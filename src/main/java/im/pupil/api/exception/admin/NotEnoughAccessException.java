package im.pupil.api.exception.admin;

public class NotEnoughAccessException extends RuntimeException {
    public NotEnoughAccessException() {
        super("Not enough access");
    }
}

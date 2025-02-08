package im.pupil.api.domain.exception.admin;

public class NotEnoughAccessException extends RuntimeException {
    public NotEnoughAccessException() {
        super("Not enough access");
    }
}

package im.pupil.api.domain.exception.relocation;

public class RelocationAlreadyExistsException extends RuntimeException{
    public RelocationAlreadyExistsException() {
        super();
    }

    public RelocationAlreadyExistsException(String message) {
        super(message);
    }
}

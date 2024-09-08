package im.pupil.api.exception.relocation;

public class RelocationAlreadyExistsException extends RuntimeException{
    public RelocationAlreadyExistsException() {
        super();
    }

    public RelocationAlreadyExistsException(String message) {
        super(message);
    }
}

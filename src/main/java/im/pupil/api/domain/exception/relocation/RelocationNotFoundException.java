package im.pupil.api.domain.exception.relocation;

public class RelocationNotFoundException extends RuntimeException{
    public RelocationNotFoundException() {
        super();
    }

    public RelocationNotFoundException(String message) {
        super(message);
    }
}

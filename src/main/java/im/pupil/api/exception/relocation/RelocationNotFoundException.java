package im.pupil.api.exception.relocation;

public class RelocationNotFoundException extends RuntimeException{
    public RelocationNotFoundException() {
        super();
    }

    public RelocationNotFoundException(String message) {
        super(message);
    }
}

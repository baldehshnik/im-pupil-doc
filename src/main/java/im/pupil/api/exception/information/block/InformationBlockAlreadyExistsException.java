package im.pupil.api.exception.information.block;

public class InformationBlockAlreadyExistsException extends RuntimeException{
    public InformationBlockAlreadyExistsException() {
        super();
    }

    public InformationBlockAlreadyExistsException(String message) {
        super(message);
    }
}

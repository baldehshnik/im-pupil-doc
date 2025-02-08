package im.pupil.api.domain.exception.information.block;

public class InformationBlockAlreadyExistsException extends RuntimeException{
    public InformationBlockAlreadyExistsException() {
        super();
    }

    public InformationBlockAlreadyExistsException(String message) {
        super(message);
    }
}

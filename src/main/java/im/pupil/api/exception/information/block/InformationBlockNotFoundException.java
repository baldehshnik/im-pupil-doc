package im.pupil.api.exception.information.block;

public class InformationBlockNotFoundException extends RuntimeException{
    public InformationBlockNotFoundException() {
        super();
    }

    public InformationBlockNotFoundException(String message) {
        super(message);
    }
}

package im.pupil.api.exception.insitution.event;

public class InstitutionEventNotFoundException extends RuntimeException {
    public InstitutionEventNotFoundException() {}

    public InstitutionEventNotFoundException(String message) {
        super(message);
    }
}

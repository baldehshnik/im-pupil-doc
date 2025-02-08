package im.pupil.api.domain.exception.insitution.event;

public class InstitutionEventNotFoundException extends RuntimeException {
    public InstitutionEventNotFoundException() {}

    public InstitutionEventNotFoundException(String message) {
        super(message);
    }
}

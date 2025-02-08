package im.pupil.api.domain.exception.event;

public class InstitutionEventNotFoundException extends RuntimeException {
    public InstitutionEventNotFoundException() {
        super("Institution event not found");
    }
}

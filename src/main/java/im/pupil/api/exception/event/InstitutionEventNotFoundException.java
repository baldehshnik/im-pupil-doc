package im.pupil.api.exception.event;

public class InstitutionEventNotFoundException extends RuntimeException {
    public InstitutionEventNotFoundException() {
        super("Institution event not found");
    }
}

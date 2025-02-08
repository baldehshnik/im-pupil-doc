package im.pupil.api.domain.exception.institution_group;

public class InstitutionGroupNotFoundException extends RuntimeException {
    public InstitutionGroupNotFoundException() {
        super("Institution group was not found");
    }
}

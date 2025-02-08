package im.pupil.api.domain.exception.institution_group;

public class InstitutionGroupWasAddedYearlyException extends RuntimeException {
    public InstitutionGroupWasAddedYearlyException() {
        super("Institution group was added yearly");
    }
}

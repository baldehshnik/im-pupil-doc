package im.pupil.api.domain.exception.educational.institution;

public class EducationalInstitutionNotFoundException extends RuntimeException{
    public EducationalInstitutionNotFoundException() {}

    public EducationalInstitutionNotFoundException(String message) {
        super(message);
    }
}

package im.pupil.api.exception.educational.institution;

public class EducationalInstitutionNotFoundException extends RuntimeException{
    public EducationalInstitutionNotFoundException() {}

    public EducationalInstitutionNotFoundException(String message) {
        super(message);
    }
}

package im.pupil.api.domain.exception.speciality;

public class SpecialityNotFoundException extends RuntimeException {
    public SpecialityNotFoundException() {
        super("Speciality was not found");
    }
}

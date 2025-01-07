package im.pupil.api.exception.speciality;

public class SpecialityNotFoundException extends RuntimeException {
    public SpecialityNotFoundException() {
        super("Speciality was not found");
    }
}

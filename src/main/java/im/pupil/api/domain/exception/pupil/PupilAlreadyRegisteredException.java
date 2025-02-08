package im.pupil.api.domain.exception.pupil;

public class PupilAlreadyRegisteredException extends RuntimeException {
    public PupilAlreadyRegisteredException() {
        super("Pupil already registered");
    }
}

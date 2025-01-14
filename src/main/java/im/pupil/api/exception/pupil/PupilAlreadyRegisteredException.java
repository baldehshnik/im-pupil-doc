package im.pupil.api.exception.pupil;

public class PupilAlreadyRegisteredException extends RuntimeException {
    public PupilAlreadyRegisteredException() {
        super("Pupil already registered");
    }
}

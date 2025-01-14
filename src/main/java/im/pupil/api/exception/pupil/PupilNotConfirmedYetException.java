package im.pupil.api.exception.pupil;

public class PupilNotConfirmedYetException extends RuntimeException {
    public PupilNotConfirmedYetException() {
        super("Pupil not confirmed yet");
    }
}

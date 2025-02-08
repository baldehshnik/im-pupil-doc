package im.pupil.api.domain.exception.pupil;

public class PupilNotConfirmedYetException extends RuntimeException {
    public PupilNotConfirmedYetException() {
        super("Pupil not confirmed yet");
    }
}

package im.pupil.api.exception.pupil;

public class PupilNotFoundException extends RuntimeException{
    public PupilNotFoundException() {
        super();
    }

    public PupilNotFoundException(String message) {
        super(message);
    }
}

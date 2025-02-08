package im.pupil.api.domain.exception.practice;

public class PracticeNotFoundException extends RuntimeException{
    public PracticeNotFoundException() {
        super();
    }

    public PracticeNotFoundException(String message) {
        super(message);
    }
}

package im.pupil.api.exception.practice;

public class PracticeNotFoundException extends RuntimeException{
    public PracticeNotFoundException() {
        super();
    }

    public PracticeNotFoundException(String message) {
        super(message);
    }
}

package im.pupil.api.exception.practice;

public class PracticeNotCreatedException extends RuntimeException{
    public PracticeNotCreatedException() {
        super();
    }

    public PracticeNotCreatedException(String message) {
        super(message);
    }
}

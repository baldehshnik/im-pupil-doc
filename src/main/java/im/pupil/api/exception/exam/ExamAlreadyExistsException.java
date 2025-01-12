package im.pupil.api.exception.exam;

public class ExamAlreadyExistsException extends RuntimeException {
    public ExamAlreadyExistsException() {
        super("Exam already exists");
    }
}

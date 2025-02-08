package im.pupil.api.domain.exception.exam;

public class ExamAlreadyExistsException extends RuntimeException {
    public ExamAlreadyExistsException() {
        super("Exam already exists");
    }
}

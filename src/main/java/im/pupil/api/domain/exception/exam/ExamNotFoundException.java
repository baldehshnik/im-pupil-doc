package im.pupil.api.domain.exception.exam;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException() {
        super("Exam not found");
    }
}

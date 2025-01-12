package im.pupil.api.exception.exam;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException() {
        super("Exam not found");
    }
}

package im.pupil.api.domain.exception.schedule;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Lesson was not found");
    }
}

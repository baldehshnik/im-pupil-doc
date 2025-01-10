package im.pupil.api.exception.schedule;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Lesson was not found");
    }
}

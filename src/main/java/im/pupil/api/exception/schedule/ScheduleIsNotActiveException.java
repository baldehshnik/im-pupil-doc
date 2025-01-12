package im.pupil.api.exception.schedule;

public class ScheduleIsNotActiveException extends RuntimeException {
    public ScheduleIsNotActiveException() {
        super("Schedule is not active");
    }
}

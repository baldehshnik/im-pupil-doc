package im.pupil.api.domain.exception.schedule;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException() {
        super("Schedule was not found");
    }
}

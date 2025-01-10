package im.pupil.api.exception.schedule;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException() {
        super("Schedule was not found");
    }
}

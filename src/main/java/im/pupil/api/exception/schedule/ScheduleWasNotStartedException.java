package im.pupil.api.exception.schedule;

public class ScheduleWasNotStartedException extends RuntimeException {
    public ScheduleWasNotStartedException() {
        super("Schedule was not started");
    }
}

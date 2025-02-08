package im.pupil.api.domain.exception.schedule;

public class ScheduleWasNotStartedException extends RuntimeException {
    public ScheduleWasNotStartedException() {
        super("Schedule was not started");
    }
}

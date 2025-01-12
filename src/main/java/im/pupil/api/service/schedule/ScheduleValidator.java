package im.pupil.api.service.schedule;

import im.pupil.api.exception.schedule.ScheduleIsNotActiveException;
import im.pupil.api.exception.schedule.ScheduleWasNotStartedException;
import im.pupil.api.model.schedule.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduleValidator {

    private final LocalDateConverter localDateConverter;

    public ScheduleValidator(LocalDateConverter localDateConverter) {
        this.localDateConverter = localDateConverter;
    }

    public void validateSchedule(Schedule schedule, LocalDate currentDate) {
        LocalDate startDate = localDateConverter.convertInstantToLocalDate(schedule.getStartDate());
        LocalDate endDate = localDateConverter.convertInstantToLocalDate(schedule.getFinishDate());

        if (startDate.isAfter(currentDate)) throw new ScheduleWasNotStartedException();
        if (currentDate.isAfter(endDate)) throw new ScheduleIsNotActiveException();
    }
}

















package im.pupil.api.service.schedule;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class WeekTypeWorker {

    public Boolean isUpperWeek(
            @NotNull Integer startType,
            @NotNull LocalDate startDate,
            @NotNull LocalDate currentDate
    ) {
        LocalDate mondayStartDate = startDate.minusDays((startDate.getDayOfWeek().getValue() + 6) % 7);
        long weeksPassed = ChronoUnit.WEEKS.between(mondayStartDate, currentDate);

        return (startType == 1) == (weeksPassed % 2 == 0);
    }

    public Integer convertToInteger(Boolean isUpperWeek) {
        if (isUpperWeek) return 1;
        else return 2;
    }

    public Boolean convertToBoolean(Integer weekType) {
        return weekType == 1;
    }
}



















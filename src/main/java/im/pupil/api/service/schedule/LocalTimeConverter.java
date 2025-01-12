package im.pupil.api.service.schedule;

import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class LocalTimeConverter {

    public Instant convertLocalTimeToInstant(LocalTime localTime, LocalDate localDate) {
        return ZonedDateTime.of(localDate, localTime, ZoneId.of("UTC")).toInstant();
    }
}



























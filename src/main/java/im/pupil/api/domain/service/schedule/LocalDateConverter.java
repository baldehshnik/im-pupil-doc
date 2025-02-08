package im.pupil.api.domain.service.schedule;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class LocalDateConverter {

    public LocalDate convertInstantToLocalDate(Instant instant) {
        long epochDay = instant.toEpochMilli() / (1000 * 60 * 60 * 24);
        return LocalDate.ofEpochDay(epochDay);
    }

    public Instant convertLocalDateToInstant(LocalDate localDate) {
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}

























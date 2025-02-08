package im.pupil.api.domain.dto.lesson;

import java.io.Serializable;

public class GetWeekDayPassDto implements Serializable {

    private Integer dayOfWeek;
    private Integer passesCount;

    public GetWeekDayPassDto() {}

    public GetWeekDayPassDto(Integer dayOfWeek, Integer passesCount) {
        this.dayOfWeek = dayOfWeek;
        this.passesCount = passesCount;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getPassesCount() {
        return passesCount;
    }

    public void setPassesCount(Integer passesCount) {
        this.passesCount = passesCount;
    }
}


























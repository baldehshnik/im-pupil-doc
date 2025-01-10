package im.pupil.api.dto.lesson;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalTime;

public class AddLessonDto implements Serializable {

    @Size(max = 64)
    @NotNull
    private String name;

    @NotNull
    private LocalTime start;

    @NotNull
    private LocalTime end;

    @Size(max = 128)
    @NotNull
    private String teacher;

    @Size(max = 10)
    @NotNull
    private String audience;

    @NotNull
    private Integer type;

    @NotNull
    private Integer dayofweek;

    @NotNull
    private Integer weekType;

    public AddLessonDto() {
    }

    public AddLessonDto(
            String name,
            LocalTime start,
            LocalTime end,
            String teacher,
            String audience,
            Integer type,
            Integer dayofweek,
            Integer weekType
    ) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
        this.audience = audience;
        this.type = type;
        this.dayofweek = dayofweek;
        this.weekType = weekType;
    }

    public @Size(max = 64) @NotNull String getName() {
        return name;
    }

    public void setName(@Size(max = 64) @NotNull String name) {
        this.name = name;
    }

    public @NotNull LocalTime getStart() {
        return start;
    }

    public void setStart(@NotNull LocalTime start) {
        this.start = start;
    }

    public @NotNull LocalTime getEnd() {
        return end;
    }

    public void setEnd(@NotNull LocalTime end) {
        this.end = end;
    }

    public @Size(max = 128) @NotNull String getTeacher() {
        return teacher;
    }

    public void setTeacher(@Size(max = 128) @NotNull String teacher) {
        this.teacher = teacher;
    }

    public @Size(max = 10) @NotNull String getAudience() {
        return audience;
    }

    public void setAudience(@Size(max = 10) @NotNull String audience) {
        this.audience = audience;
    }

    public @NotNull Integer getType() {
        return type;
    }

    public void setType(@NotNull Integer type) {
        this.type = type;
    }

    public @NotNull Integer getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(@NotNull Integer dayofweek) {
        this.dayofweek = dayofweek;
    }

    public @NotNull Integer getWeekType() {
        return weekType;
    }

    public void setWeekType(@NotNull Integer weekType) {
        this.weekType = weekType;
    }
}

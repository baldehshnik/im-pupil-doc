package im.pupil.api.dto.lesson;

import java.io.Serializable;
import java.time.LocalTime;

public class GetLessonDto implements Serializable {

    private Integer id;
    private String name;
    private LocalTime start;
    private LocalTime end;
    private String teacher;
    private String audience;
    private Integer type;
    private Integer dayofweek;
    private Integer weekType;

    public GetLessonDto() {
    }

    public GetLessonDto(
            Integer id,
            String name,
            LocalTime start,
            LocalTime end,
            String teacher,
            String audience,
            Integer type,
            Integer dayofweek,
            Integer weekType
    ) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
        this.audience = audience;
        this.type = type;
        this.dayofweek = dayofweek;
        this.weekType = weekType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(Integer dayofweek) {
        this.dayofweek = dayofweek;
    }

    public Integer getWeekType() {
        return weekType;
    }

    public void setWeekType(Integer weekType) {
        this.weekType = weekType;
    }
}
























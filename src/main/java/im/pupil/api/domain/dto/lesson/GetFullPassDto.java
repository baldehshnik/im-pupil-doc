package im.pupil.api.domain.dto.lesson;

import java.io.Serializable;
import java.time.Instant;

public class GetFullPassDto implements Serializable {

    private Integer id;
    private Instant date;
    private GetLessonDto lesson;

    public GetFullPassDto() {}

    public GetFullPassDto(Integer id, Instant date, GetLessonDto lesson) {
        this.id = id;
        this.date = date;
        this.lesson = lesson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public GetLessonDto getLesson() {
        return lesson;
    }

    public void setLesson(GetLessonDto lesson) {
        this.lesson = lesson;
    }
}




























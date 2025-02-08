package im.pupil.api.domain.dto.schedule;

import im.pupil.api.domain.dto.lesson.AddLessonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class CreateNewScheduleDto implements Serializable {

    @NotNull
    private Integer groupId;

    @Size(max = 32)
    @NotNull
    private String name;

    @NotNull
    private Instant finishDate;

    @NotNull
    private Integer startType;

    @NotNull
    private Instant startDate;

    @NotNull
    private List<AddLessonDto> lessons;

    public CreateNewScheduleDto() {}

    public CreateNewScheduleDto(
            Integer groupId,
            String name,
            Instant finishDate,
            Integer startType,
            Instant startDate,
            List<AddLessonDto> lessons
    ) {
        this.groupId = groupId;
        this.name = name;
        this.finishDate = finishDate;
        this.startType = startType;
        this.startDate = startDate;
        this.lessons = lessons;
    }

    public @NotNull Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(@NotNull Integer groupId) {
        this.groupId = groupId;
    }

    public @Size(max = 32) @NotNull String getName() {
        return name;
    }

    public void setName(@Size(max = 32) @NotNull String name) {
        this.name = name;
    }

    public @NotNull Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(@NotNull Instant finishDate) {
        this.finishDate = finishDate;
    }

    public @NotNull Integer getStartType() {
        return startType;
    }

    public void setStartType(@NotNull Integer startType) {
        this.startType = startType;
    }

    public @NotNull Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull Instant startDate) {
        this.startDate = startDate;
    }

    public List<AddLessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<AddLessonDto> lessons) {
        this.lessons = lessons;
    }
}




















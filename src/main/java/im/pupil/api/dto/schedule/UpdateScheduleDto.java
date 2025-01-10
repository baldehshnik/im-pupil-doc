package im.pupil.api.dto.schedule;

import im.pupil.api.dto.lesson.UpdateLessonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class UpdateScheduleDto implements Serializable {

    @NotNull
    private Integer groupId;

    @NotNull
    private Integer id;

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
    private Integer type;

    @NotNull
    private List<UpdateLessonDto> lessons;

    public UpdateScheduleDto() {}

    public UpdateScheduleDto(
            Integer groupId,
            Integer id,
            String name,
            Instant finishDate,
            Integer startType,
            Instant startDate,
            Integer type,
            List<UpdateLessonDto> lessons
    ) {
        this.groupId = groupId;
        this.id = id;
        this.name = name;
        this.finishDate = finishDate;
        this.startType = startType;
        this.startDate = startDate;
        this.type = type;
        this.lessons = lessons;
    }

    public @NotNull Integer getType() {
        return type;
    }

    public void setType(@NotNull Integer type) {
        this.type = type;
    }

    public @NotNull Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(@NotNull Integer groupId) {
        this.groupId = groupId;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
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

    public @NotNull List<UpdateLessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(@NotNull List<UpdateLessonDto> lessons) {
        this.lessons = lessons;
    }
}
























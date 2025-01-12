package im.pupil.api.dto.lesson;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public class UpdatePassStatusDto implements Serializable {

    @Nullable
    private Integer id;

    @NotNull
    private Integer groupMemberId;

    @NotNull
    private Integer lessonId;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer status;

    public UpdatePassStatusDto() {}

    public UpdatePassStatusDto(
            @Nullable Integer id,
            Integer groupMemberId,
            Integer lessonId,
            LocalDate date,
            Integer status
    ) {
        this.id = id;
        this.groupMemberId = groupMemberId;
        this.lessonId = lessonId;
        this.date = date;
        this.status = status;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public @NotNull Integer getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(@NotNull Integer groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public @NotNull Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(@NotNull Integer lessonId) {
        this.lessonId = lessonId;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        this.date = date;
    }

    public @NotNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }
}


























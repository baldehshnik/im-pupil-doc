package im.pupil.api.domain.dto.lesson;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class UpdatePassesStatusDto implements Serializable {

    @NotNull
    private Integer lessonId;

    @NotNull
    private LocalDate date;

    @NotNull
    private List<UpdateInfo> updateInfos;

    public UpdatePassesStatusDto() {}

    public UpdatePassesStatusDto(Integer lessonId, LocalDate date, List<UpdateInfo> updateInfos) {
        this.lessonId = lessonId;
        this.date = date;
        this.updateInfos = updateInfos;
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

    public @NotNull List<UpdateInfo> getUpdateInfos() {
        return updateInfos;
    }

    public void setUpdateInfos(@NotNull List<UpdateInfo> updateInfos) {
        this.updateInfos = updateInfos;
    }

    public static class UpdateInfo implements Serializable {

        @Nullable
        private Integer id;

        @NotNull
        private Integer groupMemberId;

        @NotNull
        private Integer status;

        public UpdateInfo() {}

        public UpdateInfo(@Nullable Integer id, Integer groupMemberId, Integer status) {
            this.id = id;
            this.groupMemberId = groupMemberId;
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

        public @NotNull Integer getStatus() {
            return status;
        }

        public void setStatus(@NotNull Integer status) {
            this.status = status;
        }
    }
}




























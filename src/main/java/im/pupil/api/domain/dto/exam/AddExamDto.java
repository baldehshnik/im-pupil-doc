package im.pupil.api.domain.dto.exam;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

public class AddExamDto implements Serializable {

    @NotNull
    private Integer groupId;

    @NotNull
    private Integer type;

    @Size(max = 32)
    @NotNull
    private String name;

    @Size(max = 10)
    @NotNull
    private String audience;

    @NotNull
    private Instant dateTime;

    public AddExamDto() {}

    public AddExamDto(Integer groupId, Integer type, String name, String audience, Instant dateTime) {
        this.groupId = groupId;
        this.type = type;
        this.name = name;
        this.audience = audience;
        this.dateTime = dateTime;
    }

    public @NotNull Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(@NotNull Integer groupId) {
        this.groupId = groupId;
    }

    public @NotNull Integer getType() {
        return type;
    }

    public void setType(@NotNull Integer type) {
        this.type = type;
    }

    public @Size(max = 32) @NotNull String getName() {
        return name;
    }

    public void setName(@Size(max = 32) @NotNull String name) {
        this.name = name;
    }

    public @Size(max = 10) @NotNull String getAudience() {
        return audience;
    }

    public void setAudience(@Size(max = 10) @NotNull String audience) {
        this.audience = audience;
    }

    public @NotNull Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(@NotNull Instant dateTime) {
        this.dateTime = dateTime;
    }
}



























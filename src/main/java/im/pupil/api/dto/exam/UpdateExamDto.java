package im.pupil.api.dto.exam;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

public class UpdateExamDto implements Serializable {

    @NotNull
    private Integer id;

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

    @NotNull
    private Integer status;

    public UpdateExamDto() {}

    public UpdateExamDto(Integer id, Integer type, String name, String audience, Instant dateTime, Integer status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.audience = audience;
        this.dateTime = dateTime;
        this.status = status;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
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

    public @NotNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }
}

























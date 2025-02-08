package im.pupil.api.domain.dto.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class AddInstitutionEventDto implements Serializable {

    @Size(max = 32)
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer type;

    public AddInstitutionEventDto() {
    }

    public AddInstitutionEventDto(String title, String description, Integer duration, Integer type) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.type = type;
    }

    public @Size(max = 32) @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@Size(max = 32) @NotNull String title) {
        this.title = title;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public @NotNull Integer getDuration() {
        return duration;
    }

    public void setDuration(@NotNull Integer duration) {
        this.duration = duration;
    }

    public @NotNull Integer getType() {
        return type;
    }

    public void setType(@NotNull Integer type) {
        this.type = type;
    }
}























package im.pupil.api.domain.dto.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UpdateInstitutionEventDto implements Serializable {

    @NotNull
    private Integer id;

    @Size(max = 32)
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String image;

    @NotNull
    private Integer duration;

    @NotNull
    private Integer type;

    public UpdateInstitutionEventDto() {}

    public UpdateInstitutionEventDto(
            Integer id,
            String title,
            String description,
            String image,
            Integer duration,
            Integer type
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.duration = duration;
        this.type = type;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
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

    public @NotNull String getImage() {
        return image;
    }

    public void setImage(@NotNull String image) {
        this.image = image;
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
























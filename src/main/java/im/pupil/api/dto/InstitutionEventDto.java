package im.pupil.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link im.pupil.api.model.InstitutionEvent}
 */
public class InstitutionEventDto implements Serializable {
    @NotNull
    @Size(max = 32)
    @NotEmpty(message = "Title should be not empty")
    @NotBlank(message = "Title should be not blank")
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String image;
    @NotNull
    private Integer duration;
    @NotNull
    private Integer type;

    public InstitutionEventDto(String title, String description, String image, Integer duration, Integer type) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.duration = duration;
        this.type = type;
    }

    public InstitutionEventDto() {}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionEventDto entity = (InstitutionEventDto) o;
        return Objects.equals(this.title, entity.title) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.image, entity.image) &&
                Objects.equals(this.duration, entity.duration) &&
                Objects.equals(this.type, entity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, image, duration, type);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "description = " + description + ", " +
                "image = " + image + ", " +
                "duration = " + duration + ", " +
                "type = " + type + ")";
    }
}
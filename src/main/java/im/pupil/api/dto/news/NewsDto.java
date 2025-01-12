package im.pupil.api.dto.news;

import im.pupil.api.model.news.News;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link News}
 */
public class NewsDto implements Serializable {

    @NotNull
    @Size(max = 128)
    @NotEmpty(message = "New's title should not be empty")
    @NotBlank(message = "New's title should not be blank")
    private String title;
    private String image;
    private String description;

    public NewsDto() {}

    public NewsDto(String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDto entity = (NewsDto) o;
        return Objects.equals(this.title, entity.title) &&
                Objects.equals(this.image, entity.image) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, image, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "image = " + image + ", " +
                "description = " + description + ")";
    }
}
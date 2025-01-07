package im.pupil.api.dto.information_block;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link im.pupil.api.model.InformationBlock}
 */
public class InformationBlockDto implements Serializable {

    @NotNull
    @Size(max = 32)
    @NotEmpty(message = "Information block should be not empty")
    @NotBlank(message = "Information block should be not blank")
    private String title;

    @NotNull
    @NotEmpty(message = "Content should be not empty")
    @NotBlank
    private String content;

    public InformationBlockDto() {
    }

    public InformationBlockDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationBlockDto entity = (InformationBlockDto) o;
        return Objects.equals(this.title, entity.title) &&
                Objects.equals(this.content, entity.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "content = " + content + ")";
    }
}
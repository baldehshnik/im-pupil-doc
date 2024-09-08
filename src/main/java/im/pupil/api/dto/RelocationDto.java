package im.pupil.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link im.pupil.api.model.Relocation}
 */
public class RelocationDto implements Serializable {
    @NotNull
    @Size(max = 32)
    @NotEmpty(message = "Relocation name should be not empty")
    @NotBlank(message = "Relocation name should be not blank")
    private String name;
    @NotNull
    private PracticeDto practice;

    public RelocationDto() {
    }

    public RelocationDto(String name, PracticeDto practice) {
        this.name = name;
        this.practice = practice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PracticeDto getPractice() {
        return practice;
    }

    public void setPractice(PracticeDto practice) {
        this.practice = practice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelocationDto entity = (RelocationDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.practice, entity.practice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, practice);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "practice = " + practice + ")";
    }
}
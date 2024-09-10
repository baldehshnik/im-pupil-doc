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
    @NotNull(message = "Name should be not null")
    @Size(max = 32)
    @NotEmpty(message = "Name should be not empty")
    @NotBlank(message = "Name should be not blank")
    private String name;

    public RelocationDto() {
    }

    public RelocationDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelocationDto entity = (RelocationDto) o;
        return Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ")";
    }
}
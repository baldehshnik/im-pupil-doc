package im.pupil.api.domain.dto.institution;

import im.pupil.api.data.entity.institution.EducationalInstitution;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link EducationalInstitution}
 */
public class EducationalInstitutionDto implements Serializable {

    @NotNull
    @NotEmpty(message = "Educational institution name should be not empty")
    @NotBlank(message = "Educational institution name should be not blank")
    private String name;

    @NotNull
    @Size(max = 10)
    @NotEmpty(message = "Educational institution abbreviation should be not empty")
    @NotBlank(message = "Educational institution abbreviation should be not blank")
    private String abbreviation;

    @NotNull
    private Integer type;

    public EducationalInstitutionDto(String name, String abbreviation, Integer type) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.type = type;
    }

    public EducationalInstitutionDto() {}

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducationalInstitutionDto entity = (EducationalInstitutionDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.abbreviation, entity.abbreviation) &&
                Objects.equals(this.type, entity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, abbreviation, type);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "abbreviation = " + abbreviation + ", " +
                "type = " + type + ")";
    }
}
package im.pupil.api.domain.dto.institution;

import im.pupil.api.data.entity.institution.EducationalInstitution;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link EducationalInstitution}
 */
public class EducationalInstitutionDto implements Serializable {

    private Integer id;

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

    public EducationalInstitutionDto(Integer id, String name, String abbreviation, Integer type) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.type = type;
    }

    public EducationalInstitutionDto() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(@NotNull @NotEmpty(message = "Educational institution name should be not empty") @NotBlank(message = "Educational institution name should be not blank") String name) {
        this.name = name;
    }

    public void setAbbreviation(@NotNull @Size(max = 10) @NotEmpty(message = "Educational institution abbreviation should be not empty") @NotBlank(message = "Educational institution abbreviation should be not blank") String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setType(@NotNull Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Integer getType() {
        return type;
    }
}
package im.pupil.api.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class GetEducationalInstitutionDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String abbreviation;

    @NotNull
    private Integer type;

    public GetEducationalInstitutionDto(String name, String abbreviation, Integer type) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.type = type;
    }

    public GetEducationalInstitutionDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetEducationalInstitutionDto entity = (GetEducationalInstitutionDto) o;
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

















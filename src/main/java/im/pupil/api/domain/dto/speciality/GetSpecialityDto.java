package im.pupil.api.domain.dto.speciality;

import java.io.Serializable;

public class GetSpecialityDto implements Serializable {

    private Integer id;
    private String name;
    private String abbreviation;

    public GetSpecialityDto(Integer id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public GetSpecialityDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
























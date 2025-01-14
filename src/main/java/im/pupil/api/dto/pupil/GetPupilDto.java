package im.pupil.api.dto.pupil;

import im.pupil.api.model.institution.EducationalInstitution;

import java.io.Serializable;

public class GetPupilDto implements Serializable {

    private Integer id;
    private String icon;
    private String code;
    private Integer status;
    private EducationalInstitution institution;

    public GetPupilDto() {
    }

    public GetPupilDto(
            Integer id,
            String icon,
            String code,
            Integer status,
            EducationalInstitution institution
    ) {
        this.id = id;
        this.icon = icon;
        this.code = code;
        this.status = status;
        this.institution = institution;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public EducationalInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(EducationalInstitution institution) {
        this.institution = institution;
    }
}



























package im.pupil.api.dto.group_member;

import java.io.Serializable;

public class EducationPlaceDto implements Serializable {

    private String institutionName;
    private String facultyName;
    private String groupName;

    public EducationPlaceDto() {}

    public EducationPlaceDto(String institutionName, String facultyName, String groupName) {
        this.institutionName = institutionName;
        this.facultyName = facultyName;
        this.groupName = groupName;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}




























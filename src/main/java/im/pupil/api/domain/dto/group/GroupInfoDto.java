package im.pupil.api.domain.dto.group;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;

import java.io.Serializable;

public class GroupInfoDto implements Serializable {

    private String institutionAbbreviation;
    private String institutionName;
    private String institutionAddress;
    private String institutionPhone;
    private String specialityName;
    private String groupName;
    private Integer groupMembersCount;

    public GroupInfoDto() {}

    public GroupInfoDto(
            String institutionAbbreviation,
            String institutionName,
            String institutionAddress,
            String institutionPhone,
            String specialityName,
            String groupName,
            Integer groupMembersCount
    ) {
        this.institutionAbbreviation = institutionAbbreviation;
        this.institutionName = institutionName;
        this.institutionAddress = institutionAddress;
        this.institutionPhone = institutionPhone;
        this.specialityName = specialityName;
        this.groupName = groupName;
        this.groupMembersCount = groupMembersCount;
    }

    @GetterMethod
    public String getInstitutionAbbreviation() {
        return institutionAbbreviation;
    }

    @SetterMethod
    public void setInstitutionAbbreviation(String institutionAbbreviation) {
        this.institutionAbbreviation = institutionAbbreviation;
    }

    @GetterMethod
    public String getInstitutionName() {
        return institutionName;
    }

    @SetterMethod
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @GetterMethod
    public String getInstitutionAddress() {
        return institutionAddress;
    }

    @SetterMethod
    public void setInstitutionAddress(String institutionAddress) {
        this.institutionAddress = institutionAddress;
    }

    @GetterMethod
    public String getInstitutionPhone() {
        return institutionPhone;
    }

    @SetterMethod
    public void setInstitutionPhone(String institutionPhone) {
        this.institutionPhone = institutionPhone;
    }

    @GetterMethod
    public String getSpecialityName() {
        return specialityName;
    }

    @SetterMethod
    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    @GetterMethod
    public String getGroupName() {
        return groupName;
    }

    @SetterMethod
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @GetterMethod
    public Integer getGroupMembersCount() {
        return groupMembersCount;
    }

    @SetterMethod
    public void setGroupMembersCount(Integer groupMembersCount) {
        this.groupMembersCount = groupMembersCount;
    }
}

























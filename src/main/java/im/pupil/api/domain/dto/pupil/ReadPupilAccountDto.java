package im.pupil.api.domain.dto.pupil;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;
import im.pupil.api.domain.dto.group.GroupInfoDto;

import java.io.Serializable;

public class ReadPupilAccountDto implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Boolean isPrefect;
    private String code;
    private OnlyPupilDto pupil;
    private GroupInfoDto groupInfo;

    public ReadPupilAccountDto() {
    }

    public ReadPupilAccountDto(
            Integer id,
            String firstname,
            String lastname,
            String patronymic,
            Boolean isPrefect,
            String code,
            OnlyPupilDto pupil,
            GroupInfoDto groupInfo
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.isPrefect = isPrefect;
        this.code = code;
        this.pupil = pupil;
        this.groupInfo = groupInfo;
    }

    @GetterMethod
    public Integer getId() {
        return id;
    }

    @SetterMethod
    public void setId(Integer id) {
        this.id = id;
    }

    @GetterMethod
    public String getFirstname() {
        return firstname;
    }

    @SetterMethod
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @GetterMethod
    public String getLastname() {
        return lastname;
    }

    @SetterMethod
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @GetterMethod
    public String getPatronymic() {
        return patronymic;
    }

    @SetterMethod
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @GetterMethod
    public Boolean getPrefect() {
        return isPrefect;
    }

    @SetterMethod
    public void setPrefect(Boolean prefect) {
        isPrefect = prefect;
    }

    @GetterMethod
    public String getCode() {
        return code;
    }

    @SetterMethod
    public void setCode(String code) {
        this.code = code;
    }

    @GetterMethod
    public OnlyPupilDto getPupil() {
        return pupil;
    }

    @SetterMethod
    public void setPupil(OnlyPupilDto pupil) {
        this.pupil = pupil;
    }

    @GetterMethod
    public GroupInfoDto getGroupInfo() {
        return groupInfo;
    }

    @SetterMethod
    public void setGroupInfo(GroupInfoDto groupInfo) {
        this.groupInfo = groupInfo;
    }
}



























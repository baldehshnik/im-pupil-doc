package im.pupil.api.domain.dto.group_member;

import im.pupil.api.data.entity.Pupil;

import java.io.Serializable;

public class GetGroupMemberDto implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Boolean isPrefect;
    private String code;
    private Pupil pupil;
    private EducationPlaceDto educationPlaceDto;

    public GetGroupMemberDto() {}

    public GetGroupMemberDto(
            Integer id,
            String firstname,
            String lastname,
            String patronymic,
            Boolean isPrefect,
            String code,
            Pupil pupil,
            EducationPlaceDto educationPlaceDto
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.isPrefect = isPrefect;
        this.code = code;
        this.pupil = pupil;
        this.educationPlaceDto = educationPlaceDto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public Boolean getPrefect() {
        return isPrefect;
    }

    public void setPrefect(Boolean prefect) {
        isPrefect = prefect;
    }

    public EducationPlaceDto getEducationPlaceDto() {
        return educationPlaceDto;
    }

    public void setEducationPlaceDto(EducationPlaceDto educationPlaceDto) {
        this.educationPlaceDto = educationPlaceDto;
    }
}
























package im.pupil.api.domain.dto.group_member;

import java.io.Serializable;

public class GetGroupMemberForListDto implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Boolean isPrefect;
    private String code;

    public GetGroupMemberForListDto() {}

    public GetGroupMemberForListDto(
            Integer id,
            String firstname,
            String lastname,
            String patronymic,
            Boolean isPrefect,
            String code
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.isPrefect = isPrefect;
        this.code = code;
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

    public Boolean getPrefect() {
        return isPrefect;
    }

    public void setPrefect(Boolean prefect) {
        isPrefect = prefect;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}




























package im.pupil.api.domain.dto.admin;

import java.io.Serializable;

public class GetAdminDto implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String email;
    private Integer accessMode;
    private String icon;

    public GetAdminDto() {}

    public GetAdminDto(
            Integer id,
            String firstname,
            String lastname,
            String patronymic,
            String email,
            Integer accessMode,
            String icon
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.accessMode = accessMode;
        this.icon = icon;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(Integer accessMode) {
        this.accessMode = accessMode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}






























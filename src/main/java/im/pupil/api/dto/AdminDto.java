package im.pupil.api.dto;

import im.pupil.api.model.Admin;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Admin}
 */
public class AdminDto implements Serializable {
    @NotNull(message = "Firstname should be not null")
    @Size(max = 32)
    @NotEmpty(message = "Firstname should be not empty")
    @NotBlank(message = "Firstname should be not blank")
    private String firstname;
    @NotNull(message = "Lastname should be not null")
    @Size(max = 32)
    @NotEmpty(message = "Lastname should be not empty")
    @NotBlank(message = "Lastname should be not blank")
    private String lastname;
    @Size(max = 32)
    private String patronymic;
    @NotNull(message = "Email should be not null")
    @Size(max = 256)
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email should be not empty")
    @NotBlank(message = "Email should be not blank")
    private String email;
    @NotNull(message = "Access mode should be not null")
    private Integer accessMode;
    private String icon;

    public AdminDto() {
    }

    public AdminDto(String firstname, String lastname, String patronymic, String email, Integer accessMode, String icon) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.accessMode = accessMode;
        this.icon = icon;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDto entity = (AdminDto) o;
        return Objects.equals(this.firstname, entity.firstname) &&
                Objects.equals(this.lastname, entity.lastname) &&
                Objects.equals(this.patronymic, entity.patronymic) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.accessMode, entity.accessMode) &&
                Objects.equals(this.icon, entity.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, patronymic, email, accessMode, icon);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstname = " + firstname + ", " +
                "lastname = " + lastname + ", " +
                "patronymic = " + patronymic + ", " +
                "email = " + email + ", " +
                "accessMode = " + accessMode + ", " +
                "icon = " + icon + ")";
    }
}
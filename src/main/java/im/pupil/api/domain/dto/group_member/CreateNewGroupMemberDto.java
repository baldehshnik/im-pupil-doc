package im.pupil.api.domain.dto.group_member;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CreateNewGroupMemberDto implements Serializable {

    @Size(max = 32)
    @NotNull
    @Column(name = "firstname", nullable = false, length = 32)
    private String firstname;

    @Size(max = 32)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 32)
    private String lastname;

    @Size(max = 32)
    @Column(name = "patronymic", length = 32)
    private String patronymic;

    @Size(max = 16)
    @NotNull
    @Column(name = "code", nullable = false, length = 16)
    private String code;

    public CreateNewGroupMemberDto() {}

    public CreateNewGroupMemberDto(String firstname, String lastname, String patronymic, String code) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.code = code;
    }

    public @Size(max = 32) @NotNull String getFirstname() {
        return firstname;
    }

    public void setFirstname(@Size(max = 32) @NotNull String firstname) {
        this.firstname = firstname;
    }

    public @Size(max = 32) @NotNull String getLastname() {
        return lastname;
    }

    public void setLastname(@Size(max = 32) @NotNull String lastname) {
        this.lastname = lastname;
    }

    public @Size(max = 32) String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(@Size(max = 32) String patronymic) {
        this.patronymic = patronymic;
    }

    public @Size(max = 16) @NotNull String getCode() {
        return code;
    }

    public void setCode(@Size(max = 16) @NotNull String code) {
        this.code = code;
    }
}

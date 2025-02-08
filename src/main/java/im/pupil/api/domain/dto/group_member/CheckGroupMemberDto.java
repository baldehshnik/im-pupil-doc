package im.pupil.api.domain.dto.group_member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class CheckGroupMemberDto implements Serializable {

    @NotNull
    private Integer institutionId;

    @Size(max = 32)
    @NotNull
    private String firstname;

    @Size(max = 32)
    @NotNull
    private String lastname;

    private String patronymic;

    @Size(max = 16)
    @NotNull
    private String code;

    public CheckGroupMemberDto(
            Integer institutionId,
            String firstname,
            String lastname,
            String patronymic,
            String code
    ) {
        this.institutionId = institutionId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.code = code;
    }

    public @NotNull Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(@NotNull Integer institutionId) {
        this.institutionId = institutionId;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public @Size(max = 16) @NotNull String getCode() {
        return code;
    }

    public void setCode(@Size(max = 16) @NotNull String code) {
        this.code = code;
    }
}






















package im.pupil.api.dto.pupil;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class AddPupilDto implements Serializable {

    @Size(max = 16)
    @NotNull
    private String code;

    @Size(max = 255)
    @NotNull
    private String email;

    @Size(max = 128)
    @NotNull
    private String password;

    @NotNull
    private Integer institutionId;

    public AddPupilDto() {}

    public AddPupilDto(String code, String email, String password, Integer institutionId) {
        this.code = code;
        this.email = email;
        this.password = password;
        this.institutionId = institutionId;
    }

    public @Size(max = 16) @NotNull String getCode() {
        return code;
    }

    public void setCode(@Size(max = 16) @NotNull String code) {
        this.code = code;
    }

    public @Size(max = 255) @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Size(max = 255) @NotNull String email) {
        this.email = email;
    }

    public @Size(max = 128) @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 128) @NotNull String password) {
        this.password = password;
    }

    public @NotNull Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(@NotNull Integer institutionId) {
        this.institutionId = institutionId;
    }
}
























package im.pupil.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link im.pupil.api.model.Pupil}
 */
public class PupilDto implements Serializable {
    @NotNull
    @Size(max = 256)
    @NotEmpty(message = "Email should be not empty")
    @NotBlank(message = "Email should be not blank")
    private String email;
    private String icon;
    @NotNull
    @Size(max = 16)
    private String code;

    public PupilDto() {}

    public PupilDto(String email, String icon, String code) {
        this.email = email;
        this.icon = icon;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PupilDto entity = (PupilDto) o;
        return Objects.equals(this.email, entity.email) &&
                Objects.equals(this.icon, entity.icon) &&
                Objects.equals(this.code, entity.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, icon, code);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "email = " + email + ", " +
                "icon = " + icon + ", " +
                "code = " + code + ")";
    }
}
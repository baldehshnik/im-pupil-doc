package im.pupil.api.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * Login DTO for {@link im.pupil.api.model.Admin}, {@link im.pupil.api.model.Pupil}, {@link im.pupil.api.model.UnconfirmedPupil}
 */
public class AuthRequestDto implements Serializable {
    @Size(max = 256)
    @NotNull
    private String email;

    @Size(max = 20)
    @NotNull
    private String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequestDto() {}

    public @Size(max = 256) @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Size(max = 256) @NotNull String email) {
        this.email = email;
    }

    public @Size(max = 20) @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 20) @NotNull String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthRequestDto entity = (AuthRequestDto) o;
        return Objects.equals(this.email, entity.email) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

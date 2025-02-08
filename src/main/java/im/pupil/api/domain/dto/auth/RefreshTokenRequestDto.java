package im.pupil.api.domain.dto.auth;

import im.pupil.api.data.entity.RefreshToken;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link RefreshToken}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenRequestDto implements Serializable {

    @NotNull
    @NotBlank
    @Size(max = 256)
    private String token;

    public @NotNull @NotBlank @Size(max = 256) String getToken() {
        return token;
    }

    public void setToken(@NotNull @NotBlank @Size(max = 256) String token) {
        this.token = token;
    }
}

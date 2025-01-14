package im.pupil.api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link im.pupil.api.model.RefreshToken}
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

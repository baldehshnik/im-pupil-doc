package im.pupil.api.domain.dto.auth;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class AuthResponseDto implements Serializable {
    @NotNull
    private String accessToken;

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthResponseDto() {}

    public @NotNull String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@NotNull String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthResponseDto that)) return false;

        return getAccessToken().equals(that.getAccessToken());
    }

    @Override
    public int hashCode() {
        return getAccessToken().hashCode();
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}

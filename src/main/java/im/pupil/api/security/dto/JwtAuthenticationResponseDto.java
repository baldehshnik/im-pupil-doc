package im.pupil.api.security.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class JwtAuthenticationResponseDto implements Serializable {

    private String accessToken;
    private String refreshToken;

    public JwtAuthenticationResponseDto() {
    }

    public JwtAuthenticationResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

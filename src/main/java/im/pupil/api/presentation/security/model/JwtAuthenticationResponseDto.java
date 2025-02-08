package im.pupil.api.presentation.security.model;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
public class JwtAuthenticationResponseDto implements Serializable {

    private String accessToken;
    private String refreshToken;

    public JwtAuthenticationResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @GetterMethod
    public String getAccessToken() {
        return accessToken;
    }

    @SetterMethod
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @GetterMethod
    public String getRefreshToken() {
        return refreshToken;
    }

    @SetterMethod
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

















package im.pupil.api.controller;

import im.pupil.api.dto.auth.RefreshTokenRequestDto;
import im.pupil.api.exception.refresh.token.RefreshTokenNotFound;
import im.pupil.api.model.RefreshToken;
import im.pupil.api.security.dto.JwtAuthenticationResponseDto;
import im.pupil.api.security.dto.SignInRequestDto;
import im.pupil.api.security.dto.admin.SignUpAdminRequestDto;
import im.pupil.api.service.AuthenticationService;
import im.pupil.api.service.JwtService;
import im.pupil.api.service.RefreshTokenService;
import im.pupil.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(
            AuthenticationService authenticationService,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            UserService userService
    ) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Operation(summary = "Create new account for admin")
    @PostMapping("/admin/sign-up")
    public JwtAuthenticationResponseDto adminSignUp(
            @Valid @RequestBody SignUpAdminRequestDto request
    ) {
        return authenticationService.adminSignUp(request);
    }

    @Operation(summary = "Sign-in into admin account")
    @PostMapping("/admin/sign-in")
    public JwtAuthenticationResponseDto adminSignIn(
            @Valid @RequestBody SignInRequestDto request
    ) {
        return authenticationService.adminSignIn(request);
    }

    @Operation(summary = "Get new access token with old refresh token")
    @PostMapping("/refreshToken")
    public JwtAuthenticationResponseDto refreshToken(
            @Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto
    ) {
        return refreshTokenService.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(userService.loadUserByUsername(user.getEmail()));
                    return JwtAuthenticationResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDto.getToken())
                            .build();
                }).orElseThrow(() -> new RefreshTokenNotFound("Refresh token is not in DB"));
    }
}

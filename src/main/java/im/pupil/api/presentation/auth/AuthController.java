package im.pupil.api.presentation.auth;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.auth.RefreshTokenRequestDto;
import im.pupil.api.domain.dto.pupil.AddPupilDto;
import im.pupil.api.domain.exception.refresh.token.RefreshTokenNotFound;
import im.pupil.api.data.entity.RefreshToken;
import im.pupil.api.presentation.security.model.JwtAuthenticationResponseDto;
import im.pupil.api.presentation.security.model.SignInRequestDto;
import im.pupil.api.presentation.security.model.SignUpAdminRequestDto;
import im.pupil.api.domain.service.JwtService;
import im.pupil.api.domain.service.RefreshTokenService;
import im.pupil.api.domain.service.UserService;
import im.pupil.api.domain.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/check-token")
    public ResponseEntity<SuccessAnswer> checkTokenValidation(@RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success token validation"));
    }

    @Operation(summary = "Create new account for pupil")
    @PostMapping("/pupil/sign-up")
    public ResponseEntity<SuccessAnswer> pupilSignUp(
            @Valid @NotNull @RequestBody AddPupilDto pupil
    ) {
        authenticationService.pupilSignUp(pupil);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success pupil registration"));
    }

    @PostMapping("/pupil/sign-in")
    public JwtAuthenticationResponseDto pupilSignIn(
            @Valid @RequestBody SignInRequestDto request
    ) {
        return authenticationService.pupilSignIn(request);
    }

    @Operation(summary = "Create new account for admin")
    @PostMapping("/admin/sign-up")
    public ResponseEntity<JwtAuthenticationResponseDto> adminSignUp(
            @Valid @RequestBody SignUpAdminRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.adminSignUp(request));
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











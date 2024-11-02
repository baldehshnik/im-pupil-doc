package im.pupil.api.controller;

import im.pupil.api.dto.RefreshTokenRequestDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RefreshTokenService refreshTokenService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserService userService;

    @Operation(summary = "Create new account for admin")
    @PostMapping("/admin/sign-up")
    public JwtAuthenticationResponseDto adminSignUp(@Valid @RequestBody SignUpAdminRequestDto request) {
        return authenticationService.adminSignUp(request);
    }

    @Operation(summary = "Sign-in into account")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDto signIn(@Valid @RequestBody SignInRequestDto request) {
        System.out.println("!!!PASSWORD " + passwordEncoder.encode(request.getPassword()) + " PASSWORD!!!");
        return authenticationService.signIn(request);
    }

    @Operation(summary = "Get new access token with old refresh token")
    @PostMapping("/refreshToken")
    public JwtAuthenticationResponseDto refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
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

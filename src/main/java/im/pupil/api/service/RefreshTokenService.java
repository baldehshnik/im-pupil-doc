package im.pupil.api.service;

import im.pupil.api.exception.refresh.token.RefreshTokenExpiredException;
import im.pupil.api.exception.user.UserNotFoundException;
import im.pupil.api.model.RefreshToken;
import im.pupil.api.repository.RefreshTokenRepository;
import im.pupil.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${token.refresh.expiration.date}")
    private Integer jwtRefreshTokenExpirationDate;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No user with email:" + email)))
                .token(tokenSupplier.get())
                .expirationDate(Instant.now().plusSeconds(jwtRefreshTokenExpirationDate))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> findByUserId(Integer userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (Instant.now().isAfter(token.getExpirationDate())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException(token.getToken() + "Refresh token is expired");
        }
        return token;
    }

    private final Supplier<String> tokenSupplier = () -> {
        //Base64 encoding takes three bytes and converts them into four characters. 192 * 4/3 = 256
        byte[] randomBytes = new byte[191];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    };
}

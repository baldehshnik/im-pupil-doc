package im.pupil.api.exception.refresh.token;

public class RefreshTokenNotFound extends RuntimeException {
    public RefreshTokenNotFound(String message) {
        super(message);
    }

    public RefreshTokenNotFound() {
        super();
    }
}

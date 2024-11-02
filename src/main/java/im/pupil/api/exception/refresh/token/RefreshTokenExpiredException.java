package im.pupil.api.exception.refresh.token;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException() {super();}

    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}

package im.pupil.api.domain.exception;

public class ErrorResponse {
    protected final String message;

    protected ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

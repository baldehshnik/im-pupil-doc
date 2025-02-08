package im.pupil.api.domain.exception;

import im.pupil.api.domain.annotation.GetterMethod;

public class ErrorResponse {

    protected final String message;

    protected ErrorResponse(String message) {
        this.message = message;
    }

    @GetterMethod
    public String getMessage() {
        return message;
    }
}

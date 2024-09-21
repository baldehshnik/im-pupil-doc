package im.pupil.api.exception.user.response;

import im.pupil.api.exception.ErrorResponse;

public class UserErrorResponse extends ErrorResponse {
    public UserErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

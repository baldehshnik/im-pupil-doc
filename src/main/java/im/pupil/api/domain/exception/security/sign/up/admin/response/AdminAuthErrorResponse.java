package im.pupil.api.domain.exception.security.sign.up.admin.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class AdminAuthErrorResponse extends ErrorResponse {
    public AdminAuthErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

package im.pupil.api.exception.admin.response;

import im.pupil.api.exception.ErrorResponse;

public class AdminErrorResponse extends ErrorResponse {
    public AdminErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

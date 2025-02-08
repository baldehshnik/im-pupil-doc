package im.pupil.api.domain.exception.admin.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class AdminErrorResponse extends ErrorResponse {
    public AdminErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

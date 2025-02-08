package im.pupil.api.domain.exception.practice.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class PracticeErrorResponse extends ErrorResponse {
    public PracticeErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

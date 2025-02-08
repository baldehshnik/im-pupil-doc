package im.pupil.api.domain.exception.refresh.token.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class RefreshTokenErrorResponse extends ErrorResponse {
    protected RefreshTokenErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

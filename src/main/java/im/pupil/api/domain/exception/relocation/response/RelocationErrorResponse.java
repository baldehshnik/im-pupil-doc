package im.pupil.api.domain.exception.relocation.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class RelocationErrorResponse extends ErrorResponse {
    public RelocationErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

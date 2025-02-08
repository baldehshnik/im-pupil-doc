package im.pupil.api.domain.exception.pupil.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class PupilErrorResponse extends ErrorResponse {
    public PupilErrorResponse(String message) {
        super(message);
    }
}

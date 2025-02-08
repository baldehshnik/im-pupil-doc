package im.pupil.api.domain.exception.storage.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class StorageErrorResponse extends ErrorResponse {
    public StorageErrorResponse(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

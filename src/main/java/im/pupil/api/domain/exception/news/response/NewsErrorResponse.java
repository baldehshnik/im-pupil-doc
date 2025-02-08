package im.pupil.api.domain.exception.news.response;

import im.pupil.api.domain.exception.ErrorResponse;

public class NewsErrorResponse extends ErrorResponse {
    public NewsErrorResponse(String message) {
        super(message);
    }
}

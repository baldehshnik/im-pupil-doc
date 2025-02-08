package im.pupil.api.domain.dto;

import jakarta.annotation.Nullable;

public class SuccessAnswer {

    String message;

    public SuccessAnswer(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static SuccessAnswer createSuccessAnswer(@Nullable String message) {
        if (message == null) return new SuccessAnswer("Success");
        return new SuccessAnswer(message);
    }
}

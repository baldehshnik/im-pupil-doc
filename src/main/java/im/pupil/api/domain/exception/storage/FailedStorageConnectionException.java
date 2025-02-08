package im.pupil.api.domain.exception.storage;

public class FailedStorageConnectionException extends RuntimeException{
    public FailedStorageConnectionException() {
        super("Failed save image");
    }

    public FailedStorageConnectionException(String message) {
        super(message);
    }
}

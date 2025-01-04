package im.pupil.api.exception.storage;

public class FailedStorageConnectionException extends RuntimeException{
    public FailedStorageConnectionException() {
        super();
    }

    public FailedStorageConnectionException(String message) {
        super(message);
    }
}

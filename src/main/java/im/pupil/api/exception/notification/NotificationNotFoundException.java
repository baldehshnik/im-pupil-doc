package im.pupil.api.exception.notification;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException() {
        super("Notification not found");
    }
}

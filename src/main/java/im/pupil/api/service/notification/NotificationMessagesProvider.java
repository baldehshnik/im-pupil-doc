package im.pupil.api.service.notification;

public enum NotificationMessagesProvider {

    NEW_ADMIN_TITLE("Новый администратор!"),
    NEW_ADMIN_DESCRIPTION("Требуется подтверждение для ");

    private final String message;

    NotificationMessagesProvider(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

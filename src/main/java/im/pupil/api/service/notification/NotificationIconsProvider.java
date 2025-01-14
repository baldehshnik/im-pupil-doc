package im.pupil.api.service.notification;

public enum NotificationIconsProvider {
    NEW_STUDENT("https://cnzmrisylnuphrekcdgi.supabase.co/storage/v1/object/public/im-pupil-pictures/notifications/new_student_notification.png"),
    NEW_ADMIN("https://cnzmrisylnuphrekcdgi.supabase.co/storage/v1/object/public/im-pupil-pictures/notifications/new_teacher_notification.png");

    private final String link;

    NotificationIconsProvider(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}

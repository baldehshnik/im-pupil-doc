package im.pupil.api.exception.admin;

public class AdminNotConfirmedYetException extends RuntimeException {
    public AdminNotConfirmedYetException() {
        super("Admin not confirmed yet");
    }
}

package im.pupil.api.domain.exception.admin;

public class AdminNotConfirmedYetException extends RuntimeException {
    public AdminNotConfirmedYetException() {
        super("Admin not confirmed yet");
    }
}

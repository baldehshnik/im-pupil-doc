package im.pupil.api.exception.admin;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException() {}

    public AdminNotFoundException(String message) {
        super(message);
    }
}

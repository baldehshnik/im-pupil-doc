package im.pupil.api.exception.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User was not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

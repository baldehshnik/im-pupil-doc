package im.pupil.api.domain.exception.security.sign.up.admin;

public class AdminAlreadyRegisteredException extends RuntimeException{
    public AdminAlreadyRegisteredException() {
        super();
    }

    public AdminAlreadyRegisteredException(String message) {
        super(message);
    }
}

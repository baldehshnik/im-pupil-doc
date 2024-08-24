package im.pupil.api.exception.news;

public class NewsNotFoundException extends RuntimeException{
    public NewsNotFoundException() {}

    public NewsNotFoundException(String message) {
        super(message);
    }
}

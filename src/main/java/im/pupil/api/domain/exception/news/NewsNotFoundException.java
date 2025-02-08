package im.pupil.api.domain.exception.news;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException() {
        super("News was not found");
    }
    public NewsNotFoundException(String message) { super(message); }
}

package im.pupil.api.exception.news;

public class NewsNotFoundException extends RuntimeException{

    public NewsNotFoundException() {
        super("News was not found");
    }
}

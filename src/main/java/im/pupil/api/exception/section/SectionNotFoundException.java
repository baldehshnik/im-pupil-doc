package im.pupil.api.exception.section;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException() {
        super("Section was not found");
    }
}

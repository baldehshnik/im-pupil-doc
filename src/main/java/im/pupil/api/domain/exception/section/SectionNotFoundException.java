package im.pupil.api.domain.exception.section;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException() {
        super("Section was not found");
    }
}

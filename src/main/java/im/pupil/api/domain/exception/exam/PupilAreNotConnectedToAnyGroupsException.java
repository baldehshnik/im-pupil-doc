package im.pupil.api.domain.exception.exam;

public class PupilAreNotConnectedToAnyGroupsException extends RuntimeException {
    public PupilAreNotConnectedToAnyGroupsException() { super("Pupil are not connected to any groups"); }
    public PupilAreNotConnectedToAnyGroupsException(String message) {
        super(message);
    }
}

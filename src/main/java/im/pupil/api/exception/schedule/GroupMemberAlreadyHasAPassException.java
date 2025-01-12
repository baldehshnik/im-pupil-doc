package im.pupil.api.exception.schedule;

public class GroupMemberAlreadyHasAPassException extends RuntimeException {
    public GroupMemberAlreadyHasAPassException() {
        super("Group member already has a pass");
    }
}

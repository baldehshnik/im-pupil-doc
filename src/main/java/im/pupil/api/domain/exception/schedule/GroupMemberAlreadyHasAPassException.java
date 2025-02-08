package im.pupil.api.domain.exception.schedule;

public class GroupMemberAlreadyHasAPassException extends RuntimeException {
    public GroupMemberAlreadyHasAPassException() {
        super("Group member already has a pass");
    }
}

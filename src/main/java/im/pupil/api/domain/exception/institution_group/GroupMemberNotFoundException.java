package im.pupil.api.domain.exception.institution_group;

public class GroupMemberNotFoundException extends RuntimeException {
    public GroupMemberNotFoundException() {
        super("Group member was not found");
    }
}

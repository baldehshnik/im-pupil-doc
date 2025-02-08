package im.pupil.api.domain.exception.institution_group;

public class GroupMemberWasAddedYearlyException extends RuntimeException {
    public GroupMemberWasAddedYearlyException() {
        super("Group member was added yearly");
    }
}

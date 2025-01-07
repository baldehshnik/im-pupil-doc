package im.pupil.api.dto.group_member;

import java.io.Serializable;

public class CanBeAddedGroupMemberDto implements Serializable {

    private Boolean canBeAdded;

    public CanBeAddedGroupMemberDto() {}

    public CanBeAddedGroupMemberDto(Boolean canBeAdded) {
        this.canBeAdded = canBeAdded;
    }

    public Boolean getCanBeAdded() {
        return canBeAdded;
    }

    public void setCanBeAdded(Boolean canBeAdded) {
        this.canBeAdded = canBeAdded;
    }
}

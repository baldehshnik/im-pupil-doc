package im.pupil.api.dto.group;

import im.pupil.api.dto.group_member.AddGroupMemberDto;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public class UpdateInstitutionGroupDto implements Serializable {

    private Integer id;

    @NotNull
    private Integer course;

    @NotNull
    private List<AddGroupMemberDto> groupMemberDtos;

    public UpdateInstitutionGroupDto() {}

    public UpdateInstitutionGroupDto(
            Integer id,
            Integer course,
            List<AddGroupMemberDto> groupMemberDtos
    ) {
        this.id = id;
        this.course = course;
        this.groupMemberDtos = groupMemberDtos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull Integer getCourse() {
        return course;
    }

    public void setCourse(@NotNull Integer course) {
        this.course = course;
    }

    public @NotNull List<AddGroupMemberDto> getGroupMemberDtos() {
        return groupMemberDtos;
    }

    public void setGroupMemberDtos(@NotNull List<AddGroupMemberDto> groupMemberDtos) {
        this.groupMemberDtos = groupMemberDtos;
    }
}
























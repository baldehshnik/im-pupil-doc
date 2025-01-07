package im.pupil.api.dto.group;

import im.pupil.api.dto.group_member.CreateNewGroupMemberDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public class CreateInstitutionGroupDto implements Serializable {

    @NotNull
    private Integer specialityId;

    @Size(max = 32)
    @NotNull
    private String name;

    @NotNull
    private Integer course;

    @NotNull
    private List<CreateNewGroupMemberDto> groupMemberDtos;

    public CreateInstitutionGroupDto(
            Integer specialityId,
            String name,
            Integer course,
            List<CreateNewGroupMemberDto> groupMemberDtos
    ) {
        this.specialityId = specialityId;
        this.name = name;
        this.course = course;
        this.groupMemberDtos = groupMemberDtos;
    }

    public @NotNull Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(@NotNull Integer specialityId) {
        this.specialityId = specialityId;
    }

    public @Size(max = 32) @NotNull String getName() {
        return name;
    }

    public void setName(@Size(max = 32) @NotNull String name) {
        this.name = name;
    }

    public @NotNull Integer getCourse() {
        return course;
    }

    public void setCourse(@NotNull Integer course) {
        this.course = course;
    }

    public @NotNull List<CreateNewGroupMemberDto> getGroupMemberDtos() {
        return groupMemberDtos;
    }

    public void setGroupMemberDtos(@NotNull List<CreateNewGroupMemberDto> groupMemberDtos) {
        this.groupMemberDtos = groupMemberDtos;
    }
}































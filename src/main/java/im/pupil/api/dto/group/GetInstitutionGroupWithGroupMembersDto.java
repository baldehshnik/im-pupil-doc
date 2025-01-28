package im.pupil.api.dto.group;

import im.pupil.api.dto.group_member.GetGroupMemberForListDto;

import java.io.Serializable;
import java.util.List;

public class GetInstitutionGroupWithGroupMembersDto implements Serializable {

    private Integer id;
    private String name;
    private Integer course;
    private List<GetGroupMemberForListDto> members;

    public GetInstitutionGroupWithGroupMembersDto() {
    }

    public GetInstitutionGroupWithGroupMembersDto(
            Integer id,
            String name,
            Integer course,
            List<GetGroupMemberForListDto> members
    ) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.members = members;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public List<GetGroupMemberForListDto> getMembers() {
        return members;
    }

    public void setMembers(List<GetGroupMemberForListDto> members) {
        this.members = members;
    }
}


































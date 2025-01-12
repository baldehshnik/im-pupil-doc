package im.pupil.api.dto.lesson;

import im.pupil.api.dto.group_member.GetGroupMemberDto;

import java.io.Serializable;

public class GetGroupMemberWithPass implements Serializable {

    private GetGroupMemberDto getGroupMemberDto;
    private GetPassDto getPassDto;

    public GetGroupMemberWithPass() {}

    public GetGroupMemberWithPass(
            GetGroupMemberDto getGroupMemberDto,
            GetPassDto getPassDto
    ) {
        this.getGroupMemberDto = getGroupMemberDto;
        this.getPassDto = getPassDto;
    }

    public GetGroupMemberDto getGetGroupMemberDto() {
        return getGroupMemberDto;
    }

    public void setGetGroupMemberDto(GetGroupMemberDto getGroupMemberDto) {
        this.getGroupMemberDto = getGroupMemberDto;
    }

    public GetPassDto getGetPassDto() {
        return getPassDto;
    }

    public void setGetPassDto(GetPassDto getPassDto) {
        this.getPassDto = getPassDto;
    }
}

























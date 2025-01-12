package im.pupil.api.dto.lesson;

import im.pupil.api.dto.group_member.GetGroupMemberDto;
import im.pupil.api.dto.group_member.GetGroupMemberPersonalInfoDto;

import java.io.Serializable;
import java.time.Instant;

public class GetFullPassWithGroupMemberDto implements Serializable {

    private Integer id;
    private Instant date;
    private GetLessonDto lesson;
    private GetGroupMemberPersonalInfoDto groupMember;

    public GetFullPassWithGroupMemberDto() {}

    public GetFullPassWithGroupMemberDto(
            Integer id,
            Instant date,
            GetLessonDto lesson,
            GetGroupMemberPersonalInfoDto groupMember
    ) {
        this.id = id;
        this.date = date;
        this.lesson = lesson;
        this.groupMember = groupMember;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public GetLessonDto getLesson() {
        return lesson;
    }

    public void setLesson(GetLessonDto lesson) {
        this.lesson = lesson;
    }

    public GetGroupMemberPersonalInfoDto getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(GetGroupMemberPersonalInfoDto groupMember) {
        this.groupMember = groupMember;
    }
}


























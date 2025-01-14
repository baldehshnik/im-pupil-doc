package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.group_member.AddGroupMemberDto;
import im.pupil.api.dto.group_member.CanBeAddedGroupMemberDto;
import im.pupil.api.dto.group_member.CheckGroupMemberDto;
import im.pupil.api.dto.group_member.GetGroupMemberDto;
import im.pupil.api.service.GroupMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education/group/member")
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    public GroupMemberController(
            GroupMemberService groupMemberService
    ) {
        this.groupMemberService = groupMemberService;
    }

    @GetMapping("/canBeAdded")
    @PreAuthorize("hasRole('ADMIN')")
    public CanBeAddedGroupMemberDto canBeAddedCheck(
            @RequestBody CheckGroupMemberDto groupMember
    ) {
        return groupMemberService.canBeAddedCheck(groupMember);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetGroupMemberDto> readGroupMembers(
            @RequestParam("groupId") Integer groupId
    ) {
        return groupMemberService.readGroupMembers(groupId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetGroupMemberDto findGroupMember(
            @PathVariable Integer id
    ) {
        return groupMemberService.readGroupMemberById(id);
    }

    @PostMapping("/prefect/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> makeAPrefect(
            @PathVariable Integer id
    ) {
        groupMemberService.makeAPrefect(id);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("New prefect was set"));
    }
}






























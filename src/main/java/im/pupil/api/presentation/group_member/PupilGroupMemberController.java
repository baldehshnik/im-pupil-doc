package im.pupil.api.presentation.group_member;

import im.pupil.api.domain.dto.group_member.GetGroupMemberDto;
import im.pupil.api.domain.service.GroupMemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pupil/group/member")
public class PupilGroupMemberController {

    private final GroupMemberService groupMemberService;

    public PupilGroupMemberController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetGroupMemberDto> readGroupMembers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return groupMemberService.readGroupMembers(email);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public GetGroupMemberDto findGroupMember(@PathVariable Integer id) {
        return groupMemberService.readGroupMemberById(id);
    }
}

























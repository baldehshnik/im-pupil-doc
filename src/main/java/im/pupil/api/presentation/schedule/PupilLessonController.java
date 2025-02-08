package im.pupil.api.presentation.schedule;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.lesson.GetGroupMemberWithPass;
import im.pupil.api.domain.dto.lesson.UpdatePassStatusDto;
import im.pupil.api.domain.dto.lesson.UpdatePassesStatusDto;
import im.pupil.api.domain.service.schedule.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pupil/schedule/lesson")
public class PupilLessonController {

    public final LessonService lessonService;

    public PupilLessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/passes")
    @PreAuthorize("hasRole('USER')")
    public List<GetGroupMemberWithPass> readPasses(
            @RequestParam("lessonId") Integer lessonId,
            @RequestParam("date") LocalDate date
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return lessonService.readGroupMembersWithPassStatus(email, lessonId, date);
    }

    @GetMapping("/pass")
    @PreAuthorize("hasRole('USER')")
    public GetGroupMemberWithPass readPassOfGroupMember(
            @RequestParam("groupMemberId") Integer groupMemberId,
            @RequestParam("lessonId") Integer lessonId,
            @RequestParam("date") LocalDate date
    ) {
        return lessonService.readGroupMemberWithPassStatus(groupMemberId, lessonId, date);
    }

    @PostMapping("/pass/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessAnswer> updatePassOfGroupMember(
            @RequestBody UpdatePassStatusDto updatePassStatus
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        lessonService.updatePassStatusByPrefect(email, updatePassStatus);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success pass status updating"));
    }

    @PostMapping("/passes/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessAnswer> updatePasses(
            @RequestBody UpdatePassesStatusDto updatePassesStatus
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        lessonService.updatePassesStatus(email, updatePassesStatus);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success passes status updating"));
    }
}
























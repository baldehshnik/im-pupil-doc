package im.pupil.api.presentation.schedule;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.lesson.GetGroupMemberWithPass;
import im.pupil.api.domain.dto.lesson.GetLessonDto;
import im.pupil.api.domain.dto.lesson.UpdatePassStatusDto;
import im.pupil.api.domain.dto.lesson.UpdatePassesStatusDto;
import im.pupil.api.domain.service.schedule.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/education/schedule/lesson")
public class LessonController {

    public final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetLessonDto findLessonById(
            @PathVariable Integer id
    ) {
        return lessonService.readLesson(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetLessonDto> findLessonsOfSchedule(
            @RequestParam("scheduleId") Integer scheduleId
    ) {
        return lessonService.readLessonsByScheduleId(scheduleId);
    }

    @GetMapping("/passes")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetGroupMemberWithPass> readPasses(
            @RequestParam("groupId") Integer groupId,
            @RequestParam("lessonId") Integer lessonId,
            @RequestParam("date") LocalDate date
    ) {
        return lessonService.readGroupMembersWithPassStatus(groupId, lessonId, date);
    }

    @GetMapping("/pass")
    @PreAuthorize("hasRole('ADMIN')")
    public GetGroupMemberWithPass readPassOfGroupMember(
            @RequestParam("groupMemberId") Integer groupMemberId,
            @RequestParam("lessonId") Integer lessonId,
            @RequestParam("date") LocalDate date
    ) {
        return lessonService.readGroupMemberWithPassStatus(groupMemberId, lessonId, date);
    }

    @PostMapping("/pass/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updatePassOfGroupMember(
            @RequestBody UpdatePassStatusDto updatePassStatus
    ) {
        lessonService.updatePassStatus(updatePassStatus);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success pass status updating"));
    }

    @PostMapping("/passes/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updatePasses(
            @RequestBody UpdatePassesStatusDto updatePassesStatus
    ) {
        lessonService.updatePassesStatus(updatePassesStatus);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success passes status updating"));
    }
}






























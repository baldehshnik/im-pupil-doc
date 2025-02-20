package im.pupil.api.presentation.schedule;

import im.pupil.api.domain.dto.lesson.GetLessonDto;
import im.pupil.api.domain.dto.lesson.GetLessonWithPassStatusDto;
import im.pupil.api.domain.dto.schedule.GetScheduleDto;
import im.pupil.api.domain.dto.schedule.GetScheduleWithLessonsDto;
import im.pupil.api.domain.service.schedule.ScheduleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pupil/schedule")
public class PupilScheduleController {

    private final ScheduleService scheduleService;

    public PupilScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/withPasses")
    @PreAuthorize("hasRole('USER')")
    public List<GetLessonWithPassStatusDto> readLessonsWithPassStatus(
            @RequestParam("groupMemberId") Integer groupMemberId,
            @RequestParam("date") LocalDate date
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return scheduleService.readLessonsWithPassStatusByPrefect(email, groupMemberId, date);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetScheduleDto> readSchedulesByGroupId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return scheduleService.readSchedulesForPupil(email);
    }

    @GetMapping("/withLessons")
    @PreAuthorize("hasRole('USER')")
    public GetScheduleWithLessonsDto readScheduleWithLessons(@RequestParam("id") Integer id) {
        return scheduleService.readScheduleWithLessons(id);
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('USER')")
    public List<GetLessonDto> readCurrentSchedule(@RequestParam("currentDate") LocalDate currentDate) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return scheduleService.readCurrentScheduleForPupil(email, currentDate);
    }
}





























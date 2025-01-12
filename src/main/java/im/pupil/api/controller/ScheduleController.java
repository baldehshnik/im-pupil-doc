package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.lesson.GetLessonDto;
import im.pupil.api.dto.lesson.GetLessonWithPassStatusDto;
import im.pupil.api.dto.schedule.CreateNewScheduleDto;
import im.pupil.api.dto.schedule.GetScheduleDto;
import im.pupil.api.dto.schedule.UpdateScheduleDto;
import im.pupil.api.service.schedule.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/education/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/withPasses")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetLessonWithPassStatusDto> readLessonsWithPassStatus(
            @RequestParam("groupMemberId") Integer groupMemberId,
            @RequestParam("date") LocalDate date
    ) {
        return scheduleService.readLessonsWithPassStatus(groupMemberId, date);
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetLessonDto> readCurrentSchedule(
            @RequestParam("groupId") Integer id,
            @RequestParam("currentDate") LocalDate currentDate
    ) {
        return scheduleService.readCurrentSchedule(id, currentDate);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetScheduleDto> readSchedulesByGroupId(
            @RequestParam("groupId") Integer id
    ) {
        return scheduleService.readSchedulesByGroupId(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> createSchedule(
            @RequestBody CreateNewScheduleDto createNewScheduleDto
    ) {
        scheduleService.createSchedule(createNewScheduleDto);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success schedule creating"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateSchedule(
            @RequestBody UpdateScheduleDto updateScheduleDto
    ) {
        scheduleService.updateSchedule(updateScheduleDto);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success schedule updating"));
    }

    @PostMapping("/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> makeScheduleAsACurrent(
            @RequestParam("scheduleId") Integer scheduleId
    ) {
        scheduleService.makeScheduleAsACurrent(scheduleId);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success status updating"));
    }

    @PostMapping("/clearStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> clearScheduleStatus(
            @RequestParam("scheduleId") Integer scheduleId
    ) {
        scheduleService.clearScheduleStatus(scheduleId);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success status updating"));
    }
}




























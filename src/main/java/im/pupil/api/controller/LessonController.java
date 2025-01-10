package im.pupil.api.controller;

import im.pupil.api.dto.lesson.GetLessonDto;
import im.pupil.api.service.LessonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}






























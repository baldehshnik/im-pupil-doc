package im.pupil.api.presentation.session;

import im.pupil.api.domain.dto.exam.GetExamDto;
import im.pupil.api.domain.service.exam.ExamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pupil/exam")
public class PupilExamController {

    private final ExamService examService;

    public PupilExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetExamDto> readExams(
            @RequestParam("date") LocalDate date
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return examService.readExams(email, date);
    }
}





























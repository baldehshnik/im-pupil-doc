package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.exam.AddExamDto;
import im.pupil.api.dto.exam.DeleteExamsDto;
import im.pupil.api.dto.exam.GetExamDto;
import im.pupil.api.dto.exam.UpdateExamDto;
import im.pupil.api.service.exam.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/education/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/all")
    public List<GetExamDto> readExams(
            @RequestParam("groupId") Integer groupId,
            @RequestParam("date") LocalDate date
    ) {
        return examService.readExams(groupId, date);
    }

    @GetMapping("/{id}")
    public GetExamDto readExamById(
            @PathVariable Integer id
    ) {
        return examService.readExamById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> deleteExams(
            @RequestBody DeleteExamsDto examsIds
    ) {
        examService.deleteExams(examsIds);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success exams deleting"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> createExam(
            @RequestBody AddExamDto exam
    ) {
        examService.createExam(exam);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success exam creating"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateExam(
            @RequestBody UpdateExamDto exam
    ) {
        examService.updateExam(exam);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success exam updating"));
    }
}





























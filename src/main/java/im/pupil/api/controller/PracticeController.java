package im.pupil.api.controller;

import im.pupil.api.dto.PracticeDto;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.exception.practice.response.PracticeErrorResponse;
import im.pupil.api.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/education/practice")
public class PracticeController {
    private final PracticeService practiceService;

    @Autowired
    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping("/search/id/{id}")
    public PracticeDto getPracticeWithPracticeId(@PathVariable Integer id) {
        return practiceService.convertToDto(practiceService.findPracticeById(id));
    }

    @GetMapping("/search/institution/id/{institution_id}")
    public List<PracticeDto> getPracticesWithInstitutionId(@PathVariable Integer institution_id) {
        return practiceService
                .findPracticesByInstitutionId(institution_id)
                .stream()
                .map(practiceService::convertToDto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<PracticeErrorResponse> handlePracticeNotFoundException(PracticeNotFoundException exception) {
        PracticeErrorResponse practiceErrorResponse = new PracticeErrorResponse(exception.getMessage());

        return new ResponseEntity<>(practiceErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EducationalInstitutionErrorResponse> handleNotFoundEducationInstitutionException(
            EducationalInstitutionNotFoundException exception){
        EducationalInstitutionErrorResponse errorResponse = new EducationalInstitutionErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }
}

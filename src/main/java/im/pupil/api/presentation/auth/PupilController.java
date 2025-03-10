package im.pupil.api.presentation.auth;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.pupil.GetPupilDto;
import im.pupil.api.domain.dto.pupil.ReadPupilAccountDto;
import im.pupil.api.domain.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.domain.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.domain.service.auth.PupilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pupil")
public class PupilController {

    private final PupilService pupilService;

    public PupilController(PupilService pupilService) {
        this.pupilService = pupilService;
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('USER')")
    public ReadPupilAccountDto readPupilAccount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return pupilService.readPupilAccount(email);
    }

    @GetMapping("/notConfirmed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetPupilDto> readNotConfirmedPupils(
            @RequestParam("institutionId") Integer institutionId
    ) {
        return pupilService.readNotConfirmedPupils(institutionId);
    }

    @PostMapping("/confirm/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> confirmPupil(
            @PathVariable Integer id
    ) {
        pupilService.confirmPupil(id);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success pupil confirmation"));
    }

    @ExceptionHandler
    private ResponseEntity<EducationalInstitutionErrorResponse> handleNotFoundEducationInstitutionException(
            EducationalInstitutionNotFoundException exception) {
        EducationalInstitutionErrorResponse errorResponse = new EducationalInstitutionErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }
}















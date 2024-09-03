package im.pupil.api.controller;

import im.pupil.api.dto.PupilDto;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.exception.pupil.PupilNotFoundException;
import im.pupil.api.exception.pupil.response.PupilErrorResponse;
import im.pupil.api.service.PupilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pupil")
public class PupilController {
    private final PupilService pupilService;

    @Autowired
    public PupilController(PupilService pupilService) {
        this.pupilService = pupilService;
    }

    @Operation(summary = "Get pupil by id")
    @ApiResponse(
            responseCode = "200",
            description = "Found pupil",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pupil not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilErrorResponse.class))
            }
    )
    @GetMapping("/account/search/id/{id}")
    PupilDto getPupilById(@PathVariable Integer id) {
        return pupilService.convertToDto(pupilService.findById(id));
    }

    @Operation(summary = "Get pupil by email")
    @ApiResponse(
            responseCode = "200",
            description = "Found pupil",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pupil not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilErrorResponse.class))
            }
    )
    @GetMapping("/account/search/email/{email}")
    PupilDto getPupilByEmail(@PathVariable String email) {
        return pupilService.convertToDto(pupilService.findByEmail(email));
    }

    @Operation(summary = "Get pupil by code")
    @ApiResponse(
            responseCode = "200",
            description = "Found pupil",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pupil not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilErrorResponse.class))
            }
    )
    @GetMapping("/account/search/code/{code}")
    PupilDto getPupilByCode(@PathVariable String code) {
        return pupilService.convertToDto(pupilService.findByCode(code));
    }

    @Operation(summary = "Get a list of pupils by institution ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found the list of pupils",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilDto.class, type = "array"))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pupils not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = PupilErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for student finding, not existing",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @GetMapping("/account/search/institution/id/{id}")
    List<PupilDto> getListOfPupilByInstitutionId(@PathVariable Integer id) {
        return pupilService
                .findByInstitutionId(id)
                .stream()
                .map((pupilService::convertToDto))
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<PupilErrorResponse> handlePupilNotFoundException(PupilNotFoundException exception) {
        PupilErrorResponse pupilErrorResponse = new PupilErrorResponse(exception.getMessage());

        return new ResponseEntity<>(pupilErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EducationalInstitutionErrorResponse> handleNotFoundEducationInstitutionException(
            EducationalInstitutionNotFoundException exception){
        EducationalInstitutionErrorResponse errorResponse = new EducationalInstitutionErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }
}
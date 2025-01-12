package im.pupil.api.controller;

import im.pupil.api.dto.admin.AdminDto;
import im.pupil.api.dto.institution.EducationalInstitutionDto;
import im.pupil.api.dto.InstitutionEventDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.admin.response.AdminErrorResponse;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.exception.insitution.event.InstitutionEventNotFoundException;
import im.pupil.api.exception.insitution.event.response.InstitutionEventErrorResponse;
import im.pupil.api.service.EducationalInstitutionService;
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
@RequestMapping("/education/institution")
public class EducationalInstitutionController {
    private final EducationalInstitutionService educationalInstitutionService;
    private final EducationalInstitutionService.EducationInstitutionAssociatedConverters educationalInstitutionConverters;

    @Autowired
    public EducationalInstitutionController(EducationalInstitutionService educationalInstitutionService) {
        this.educationalInstitutionService = educationalInstitutionService;
        this.educationalInstitutionConverters = educationalInstitutionService.getEducationInstitutionAssociatedConverters();
    }

    @GetMapping("/byNamePart")
    public List<EducationalInstitutionDto> readEducationalInstitutionsByNamePart(
            @RequestParam("namePart") String namePart
    ) {
        return educationalInstitutionService.getEducationalInstitutionByNamePart(namePart);
    }

    @Operation(summary = "Get a list of institution events by institution ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found the list of institution events",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InstitutionEventDto.class, type = "array"))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Institution events not found",
            content = { @Content (
                mediaType = "application/json",
                schema = @Schema(implementation = InstitutionEventErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for event finding, not existing",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @GetMapping("/events/id/{institutionId}")
    public List<InstitutionEventDto> getEventsByInstitutionId(@PathVariable Integer institutionId) {
        return educationalInstitutionService
                .getInstitutionEventsOfEducationInstitutionById(institutionId)
                .stream()
                .map(educationalInstitutionConverters::convertInstitutionEventToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a list of institution events by institution name")
    @ApiResponse(
            responseCode = "200",
            description = "Found the list of institution events",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InstitutionEventDto.class, type = "array"))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Institution events not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = InstitutionEventErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for event finding, not existing",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @GetMapping("/events/institutionName/{institutionName}")
    public List<InstitutionEventDto> getEventsByInstitutionName(@PathVariable String institutionName) {
        return educationalInstitutionService
                .getInstitutionEventsOfEducationInstitutionByName(institutionName)
                .stream()
                .map(educationalInstitutionConverters::convertInstitutionEventToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a list of institution events by institution abbreviation")
    @ApiResponse(
            responseCode = "200",
            description = "Found the list of institution events",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InstitutionEventDto.class, type = "array"))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Institution events not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = InstitutionEventErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for event finding, not existing",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @GetMapping("/events/instituteAbbreviation/{institutionAbbreviation}")
    public List<InstitutionEventDto> getEventsByInstitutionAbbreviation(@PathVariable String institutionAbbreviation) {
        return educationalInstitutionService
                .getInstitutionEventsOfEducationInstitutionByAbbreviation(institutionAbbreviation)
                .stream()
                .map(educationalInstitutionConverters::convertInstitutionEventToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a list of admins by institution id")
    @ApiResponse(
            responseCode = "200",
            description = "Found the list of admins",
            content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDto.class, type = "array"))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Admins not found",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for admin finding, not existing",
            content = { @Content (
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @GetMapping("/admins/id/{institutionId}")
    public List<AdminDto> getAdminsOfInstitutionById(@PathVariable Integer institutionId) {
        return educationalInstitutionService.getAdminsOfEducationalInstitutionByInstitutionId(institutionId)
                .stream()
                .map(educationalInstitutionConverters::convertAdminToDto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<InstitutionEventErrorResponse> handleNotFoundInstitutionEventException(
            InstitutionEventNotFoundException exception){
        InstitutionEventErrorResponse errorResponse = new InstitutionEventErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EducationalInstitutionErrorResponse> handleNotFoundEducationInstitutionException(
            EducationalInstitutionNotFoundException exception){
        EducationalInstitutionErrorResponse errorResponse = new EducationalInstitutionErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleNotFoundAdminException(
            AdminNotFoundException exception){
        AdminErrorResponse errorResponse = new AdminErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
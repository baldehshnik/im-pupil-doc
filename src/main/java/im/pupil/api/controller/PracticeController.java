package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.practice.*;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.exception.information.block.InformationBlockAlreadyExistsException;
import im.pupil.api.exception.information.block.response.InformationBlockErrorResponse;
import im.pupil.api.exception.practice.PracticeNotCreatedException;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.exception.practice.response.PracticeErrorResponse;
import im.pupil.api.exception.relocation.RelocationAlreadyExistsException;
import im.pupil.api.exception.relocation.response.RelocationErrorResponse;
import im.pupil.api.service.InformationBlockService;
import im.pupil.api.service.PracticeService;
import im.pupil.api.service.RelocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/education/practice")
public class PracticeController {

    private final PracticeService practiceService;
    private final RelocationService relocationService;
    private final InformationBlockService informationBlockService;

    @Autowired
    public PracticeController(
            PracticeService practiceService,
            RelocationService relocationService,
            InformationBlockService informationBlockService
    ) {
        this.practiceService = practiceService;
        this.relocationService = relocationService;
        this.informationBlockService = informationBlockService;
    }

    @Operation(summary = "Get practice by ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found practice",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeDto.class))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Practice not found",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeErrorResponse.class))
            }
    )
    @GetMapping("/search/{id}")
    public GetPracticeDto getPracticeWithPracticeId(@PathVariable Integer id) {
        return practiceService.convertToPracticeDto(practiceService.findPracticeById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> deletePracticeById(@PathVariable Integer id) {
        practiceService.deletePractice(id);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success practice deleting!"));
    }

    @Operation(summary = "Get a list of practices in specific education institution")
    @ApiResponse(
            responseCode = "200",
            description = "Found the list of practices",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeDto.class, type = "array"))
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Practice not found",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for practice finding, not existing",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @GetMapping("/search/byInstitution/{institutionId}")
    public List<GetListPracticeDto> getPracticesWithInstitutionId(@PathVariable Integer institutionId) {
        return practiceService.findPracticesByInstitutionId(institutionId).stream()
                .map(practiceService::convertToListDto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<PracticeErrorResponse> handlePracticeNotFoundException(PracticeNotFoundException exception) {
        PracticeErrorResponse practiceErrorResponse = new PracticeErrorResponse(exception.getMessage());

        return new ResponseEntity<>(practiceErrorResponse, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create new practice",
            description = "Creates new practice with new relocation and new description block. " +
                    "If any of the records does not meet the uniqueness conditions, " +
                    "than AlreadyExistsException will be thrown")
    @ApiResponse(
            responseCode = "201",
            description = "Create new practice, without content in response"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Practice not created",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "406",
            description = "Practice constraints violated",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "409",
            description = "Relocation already exists",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "409",
            description = "Information block already exists",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PracticeErrorResponse.class))
            }
    )
    @ApiResponse(
            responseCode = "424",
            description = "Education institution, which is used for practice finding, not existing",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EducationalInstitutionErrorResponse.class))
            }
    )
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> performPractiseRegistration(
            @RequestBody @Valid CreatePracticeDto practiceDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new PracticeNotCreatedException(buildErrorMessageByBindingResult(bindingResult));
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        practiceService.createPracticeWithRelocationInformationBlockWithExistingEducationInstitution(
                email,
                practiceDto,
                practiceDto.getRelocations().stream()
                        .map(relocationService::convertToEntity).collect(Collectors.toSet()),
                practiceDto.getInformationBlocks().stream()
                        .map(informationBlockService::convertToEntity).collect(Collectors.toSet())
        );

        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer(null));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> performPracticeRegistration(
            @RequestBody @Valid UpdatePracticeDto practiceDto
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        practiceService.updatePracticeWithRelocationInformationBlockWithExistingEducationInstitution(
                email,
                practiceDto,
                practiceDto.getRelocations(),
                practiceDto.getInformationBlocks()
        );

        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer(null));
    }

    @ExceptionHandler
    private ResponseEntity<EducationalInstitutionErrorResponse> handleNotFoundEducationInstitutionException(
            EducationalInstitutionNotFoundException exception) {
        EducationalInstitutionErrorResponse errorResponse = new EducationalInstitutionErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    private ResponseEntity<PracticeErrorResponse> handlePracticeNotCreatedException(
            PracticeNotCreatedException exception) {
        PracticeErrorResponse errorResponse = new PracticeErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PracticeErrorResponse> handleConstraintViolationException(
            ConstraintViolationException exception) {
        PracticeErrorResponse errorResponse = new PracticeErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    private ResponseEntity<RelocationErrorResponse> handleRelocationAlreadyExistsException(
            RelocationAlreadyExistsException exception) {
        RelocationErrorResponse errorResponse = new RelocationErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<InformationBlockErrorResponse> handleRelocationAlreadyExistsException(
            InformationBlockAlreadyExistsException exception) {
        InformationBlockErrorResponse errorResponse = new InformationBlockErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    private String buildErrorMessageByBindingResult(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getFieldErrors().forEach(
                (errorField) -> stringBuilder
                        .append(errorField.getField())
                        .append(" - ")
                        .append(errorField.getDefaultMessage())
                        .append("; ")
        );
        return stringBuilder.toString();
    }
}

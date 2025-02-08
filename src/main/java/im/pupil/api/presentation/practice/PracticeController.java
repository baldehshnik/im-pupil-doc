package im.pupil.api.presentation.practice;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.practice.*;
import im.pupil.api.domain.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.domain.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.domain.exception.practice.PracticeNotCreatedException;
import im.pupil.api.domain.exception.practice.response.PracticeErrorResponse;
import im.pupil.api.domain.parser.JsonParser;
import im.pupil.api.domain.service.InformationBlockService;
import im.pupil.api.domain.service.PracticeService;
import im.pupil.api.domain.service.RelocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/education/practice")
public class PracticeController {

    private final PracticeService practiceService;
    private final RelocationService relocationService;
    private final InformationBlockService informationBlockService;

    private final JsonParser jsonParser;

    public PracticeController(
            PracticeService practiceService,
            RelocationService relocationService,
            InformationBlockService informationBlockService,
            JsonParser jsonParser
    ) {
        this.practiceService = practiceService;
        this.relocationService = relocationService;
        this.informationBlockService = informationBlockService;
        this.jsonParser = jsonParser;
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
        return practiceService.findPracticeById(id);
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
    public ResponseEntity<SuccessAnswer> performPracticeRegistration(
            @RequestPart("practice") String practiceDto,
            @Valid @NotNull @RequestPart("image") MultipartFile image,
            BindingResult bindingResult
    ) throws UnexpectedException {
        if (bindingResult.hasErrors()) {
            throw new PracticeNotCreatedException(buildErrorMessageByBindingResult(bindingResult));
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CreatePracticeDto dto = jsonParser.parseJsonToDto(practiceDto, CreatePracticeDto.class);
        practiceService.createPracticeWithRelocationInformationBlockWithExistingEducationInstitution(
                email,
                dto,
                dto.getRelocations().stream()
                        .map(relocationService::convertToEntity)
                        .collect(Collectors.toSet()),
                dto.getInformationBlocks().stream()
                        .map(informationBlockService::convertToEntity)
                        .collect(Collectors.toSet()),
                image
        );

        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success practice creating"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updatePracticeRegistration(
            @RequestPart("practice") String practiceDto,
            @Nullable @RequestPart("image") MultipartFile image
    ) throws UnexpectedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UpdatePracticeDto dto = jsonParser.parseJsonToDto(practiceDto, UpdatePracticeDto.class);
        practiceService.updatePracticeWithRelocationInformationBlockWithExistingEducationInstitution(
                email,
                dto,
                dto.getRelocations(),
                dto.getInformationBlocks(),
                image
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
    private ResponseEntity<PracticeErrorResponse> handleConstraintViolationException(
            ConstraintViolationException exception) {
        PracticeErrorResponse errorResponse = new PracticeErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
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

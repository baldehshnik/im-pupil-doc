package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.event.AddInstitutionEventDto;
import im.pupil.api.dto.event.GetInstitutionEventDto;
import im.pupil.api.dto.event.UpdateInstitutionEventDto;
import im.pupil.api.service.event.InstitutionEventService;
import im.pupil.api.worker.parser.JsonParser;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;

@RestController
@RequestMapping("/education/event")
public class InstitutionEventController {

    private final InstitutionEventService institutionEventService;

    private final JsonParser jsonParser;

    public InstitutionEventController(InstitutionEventService institutionEventService, JsonParser jsonParser) {
        this.institutionEventService = institutionEventService;
        this.jsonParser = jsonParser;
    }

    @GetMapping("/{eventId}")
    public GetInstitutionEventDto readInstitutionEvent(
            @PathVariable Integer eventId
    ) {
        return institutionEventService.readInstitutionEvent(eventId);
    }

    @GetMapping("/all")
    public List<GetInstitutionEventDto> readInstitutionEvents(
            @Valid @NotNull @RequestParam("institutionId") Integer id
    ) {
        return institutionEventService.readInstitutionEvents(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> createNewEvent(
            @Valid @NotNull @RequestPart("event") String addInstitutionEventDto,
            @Valid @NotNull @RequestPart("image") MultipartFile image
    ) throws UnexpectedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AddInstitutionEventDto institutionEventDto = jsonParser.parseJsonToDto(addInstitutionEventDto, AddInstitutionEventDto.class);
        institutionEventService.createNewEvent(email, institutionEventDto, image);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success event creating"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateEvent(
            @Valid @NotNull @RequestPart("event") String updateInstitutionEventDto,
            @Valid @Nullable @RequestPart("image") MultipartFile image
    ) throws UnexpectedException {
        UpdateInstitutionEventDto institutionEventDto = jsonParser.parseJsonToDto(updateInstitutionEventDto, UpdateInstitutionEventDto.class);
        institutionEventService.updateEvent(institutionEventDto, image);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success event updating"));
    }
}

























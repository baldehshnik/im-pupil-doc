package im.pupil.api.presentation.institution_event;

import im.pupil.api.config.SecurityConfig;
import im.pupil.api.domain.dto.event.GetInstitutionEventDto;
import im.pupil.api.domain.service.event.InstitutionEventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pupil/institution/event")
public class PupilInstitutionEventController {

    private final InstitutionEventService institutionEventService;

    public PupilInstitutionEventController(InstitutionEventService institutionEventService) {
        this.institutionEventService = institutionEventService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetInstitutionEventDto> readInstitutionEvents() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return institutionEventService.readInstitutionEventForPupil(email);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public GetInstitutionEventDto readInstitutionEvent(
            @PathVariable Integer id
    ) {
        return institutionEventService.readInstitutionEvent(id);
    }
}































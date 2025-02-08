package im.pupil.api.presentation.section;

import im.pupil.api.domain.dto.section.GetSectionDto;
import im.pupil.api.domain.service.SectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pupil/section")
public class PupilSectionController {

    private final SectionService sectionService;

    public PupilSectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetSectionDto> readSections() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return sectionService.readSectionsForPupil(email);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public GetSectionDto findSectionById(@PathVariable Integer id) {
        return sectionService.findSectionById(id);
    }
}
























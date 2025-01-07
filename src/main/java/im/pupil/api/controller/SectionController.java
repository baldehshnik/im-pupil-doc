package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.section.CreateSectionDto;
import im.pupil.api.dto.section.GetSectionDto;
import im.pupil.api.dto.section.UpdateSectionDto;
import im.pupil.api.service.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education/section")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetSectionDto> readSections(
            @RequestParam("institutionId") Integer institutionId
    ) {
        return sectionService.readSections(institutionId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetSectionDto findSectionById(@PathVariable Integer id) {
        return sectionService.findSectionById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> deleteSectionById(@PathVariable Integer id) {
        sectionService.deleteSectionById(id);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success section deleting"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> createSection(
            @RequestBody CreateSectionDto createSectionDto
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        sectionService.createSection(email, createSectionDto);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success section creation"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateSection(
            @RequestBody UpdateSectionDto updateSectionDto
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        sectionService.updateSection(email, updateSectionDto);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success section updating"));
    }
}






















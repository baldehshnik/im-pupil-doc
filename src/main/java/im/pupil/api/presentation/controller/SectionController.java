package im.pupil.api.presentation.controller;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.section.CreateSectionDto;
import im.pupil.api.domain.dto.section.GetSectionDto;
import im.pupil.api.domain.dto.section.UpdateSectionDto;
import im.pupil.api.domain.service.SectionService;
import im.pupil.api.domain.parser.JsonParser;
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
@RequestMapping("/education/section")
public class SectionController {

    private final SectionService sectionService;

    private final JsonParser jsonParser;

    public SectionController(
            SectionService sectionService,
            JsonParser jsonParser
    ) {
        this.sectionService = sectionService;
        this.jsonParser = jsonParser;
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
            @RequestPart("section") String createSectionDto,
            @Valid @NotNull @RequestPart("image") MultipartFile image
    ) throws UnexpectedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        sectionService.createSection(email, jsonParser.parseJsonToDto(createSectionDto, CreateSectionDto.class), image);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success section creation"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateSection(
            @RequestPart("section") String updateSectionDto,
            @Valid @Nullable @RequestPart("image") MultipartFile image
    ) throws UnexpectedException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        sectionService.updateSection(email, jsonParser.parseJsonToDto(updateSectionDto, UpdateSectionDto.class), image);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success section updating"));
    }
}






















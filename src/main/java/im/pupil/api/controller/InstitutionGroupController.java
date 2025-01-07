package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.group.CreateInstitutionGroupDto;
import im.pupil.api.dto.group.GetInstitutionGroup;
import im.pupil.api.dto.group.UpdateInstitutionGroupDto;
import im.pupil.api.service.InstitutionGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education/group")
public class InstitutionGroupController {

    private final InstitutionGroupService institutionGroupService;

    public InstitutionGroupController(InstitutionGroupService institutionGroupService) {
        this.institutionGroupService = institutionGroupService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetInstitutionGroup> readInstitutionGroupsBySpecialityId(
            @RequestParam("specialityId") Integer id
    ) {
        return institutionGroupService.readGroupsBySpecialityId(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> createInstitutionGroup(
            @RequestBody CreateInstitutionGroupDto createInstitutionGroupDto
    ) {
        institutionGroupService.createGroup(createInstitutionGroupDto);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success new group creation"));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateInstitutionGroup(
            @RequestBody UpdateInstitutionGroupDto updateInstitutionGroupDto
    ) {
        institutionGroupService.updateGroup(updateInstitutionGroupDto);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success group updating"));
    }
}
























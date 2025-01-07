package im.pupil.api.controller;

import im.pupil.api.dto.speciality.GetSpecialityDto;
import im.pupil.api.service.SpecialityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education/speciality")
public class SpecialityController {

    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping("/byFaculty")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetSpecialityDto> readSpecialitiesByFacultyId(
        @RequestParam("facultyId") Integer id
    ) {
        return specialityService.readSpecialitiesByFacultyId(id);
    }
}
























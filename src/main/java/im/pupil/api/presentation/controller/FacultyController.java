package im.pupil.api.presentation.controller;

import im.pupil.api.domain.dto.faculty.GetFacultyDto;
import im.pupil.api.domain.service.FacultyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetFacultyDto> readFacultiesWithAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return facultyService.readFacultiesByInstitutionId(email);
    }
}





























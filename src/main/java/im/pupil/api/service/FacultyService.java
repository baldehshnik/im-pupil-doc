package im.pupil.api.service;

import im.pupil.api.dto.faculty.GetFacultyDto;
import im.pupil.api.model.Admin;
import im.pupil.api.model.EducationalInstitution;
import im.pupil.api.model.Faculty;
import im.pupil.api.repository.FacultyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final EducationalInstitutionService educationalInstitutionService;
    private final AdminService adminService;

    private final ModelMapper modelMapper;

    public FacultyService(
            FacultyRepository facultyRepository,
            EducationalInstitutionService educationalInstitutionService,
            AdminService adminService,
            ModelMapper modelMapper
    ) {
        this.facultyRepository = facultyRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetFacultyDto> readFacultiesByInstitutionId(String email) {
        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(admin.getInstitution().getId());
        List<Faculty> faculties = facultyRepository.readFacultiesByInstitutionId(educationalInstitution.getId());

        return faculties.stream()
                .map(m -> modelMapper.map(m, GetFacultyDto.class))
                .toList();
    }
}




























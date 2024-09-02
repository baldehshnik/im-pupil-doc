package im.pupil.api.service;

import im.pupil.api.dto.AdminDto;
import im.pupil.api.dto.InstitutionEventDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.insitution.event.InstitutionEventNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.EducationalInstitution;
import im.pupil.api.model.InstitutionEvent;
import im.pupil.api.repository.AdminRepository;
import im.pupil.api.repository.EducationalInstitutionRepository;
import im.pupil.api.repository.InstitutionEventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EducationalInstitutionService {

    final EducationalInstitutionRepository educationalInstitutionRepository;
    final InstitutionEventRepository institutionEventRepository;
    final AdminRepository adminRepository;
    final AdminService adminService;
    final ModelMapper modelMapper;

    @Autowired
    public EducationalInstitutionService(EducationalInstitutionRepository educationalInstitutionRepository,
                                         InstitutionEventRepository institutionEventRepository,
                                         AdminRepository adminRepository,
                                         AdminService adminService,
                                         ModelMapper modelMapper) {
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.institutionEventRepository = institutionEventRepository;
        this.adminRepository = adminRepository;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    public List<InstitutionEvent> getInstitutionEventsOfEducationInstitutionById(Integer institutionId) {
        EducationalInstitution educationalInstitution = findEducationalInstitutionById(institutionId);
        List<InstitutionEvent> result = institutionEventRepository.findByInstitutionId(educationalInstitution.getId());

        if (result.isEmpty()) {
            throw new InstitutionEventNotFoundException("No institution event found with institution id " + institutionId);
        }

        return result;
    }

    public List<InstitutionEvent> getInstitutionEventsOfEducationInstitutionByAbbreviation(String institutionAbbreviation) {
        EducationalInstitution educationalInstitution = findEducationalInstitutionByAbbreviation(institutionAbbreviation);
        List<InstitutionEvent> result = institutionEventRepository.findByInstitutionId(
                educationalInstitution.getId()
        );

        if (result.isEmpty()) {
            throw new InstitutionEventNotFoundException("No institution event found with institution abbreviation " + institutionAbbreviation);
        }

        return result;
    }

    public List<InstitutionEvent> getInstitutionEventsOfEducationInstitutionByName(String institutionName) {
        EducationalInstitution educationalInstitution = findEducationalInstitutionByName(institutionName);
        List<InstitutionEvent> result = institutionEventRepository.findByInstitutionId(
                educationalInstitution.getId()
        );

        if (result.isEmpty()) {
            throw new InstitutionEventNotFoundException("No institution event found with institution name " + institutionName);
        }

        return result;
    }

    public List<Admin> getAdminsOfEducationalInstitutionByInstitutionId(Integer institutionId) {
        EducationalInstitution educationalInstitution = findEducationalInstitutionById(institutionId);
        List<Admin> admins = adminRepository.findByInstitution_Id(educationalInstitution.getId());

        if (admins.isEmpty()) {
            throw new AdminNotFoundException("No admin found with institution id " + institutionId);
        }

        return admins;
    }

    private EducationalInstitution findEducationalInstitutionById(Integer institutionId) {
        Optional<EducationalInstitution> educationalInstitution = educationalInstitutionRepository.findById(institutionId);
        return educationalInstitution.orElseThrow(() -> new EducationalInstitutionNotFoundException("No educational institution found with id: " + institutionId));
    }

    private EducationalInstitution findEducationalInstitutionByName(String institutionName) {
        Optional<EducationalInstitution> educationalInstitution = educationalInstitutionRepository.findByName(institutionName);
        return educationalInstitution.orElseThrow(() -> new EducationalInstitutionNotFoundException("No educational institution found with name: " + institutionName));
    }

    private EducationalInstitution findEducationalInstitutionByAbbreviation(String institutionAbbreviation) {
        Optional<EducationalInstitution> educationalInstitution = educationalInstitutionRepository.findByAbbreviation(institutionAbbreviation);
        return educationalInstitution.orElseThrow(() -> new EducationalInstitutionNotFoundException("No educational institution found with abbreviation: " + institutionAbbreviation));
    }

    public EducationInstitutionAssociatedConverters getEducationInstitutionAssociatedConverters() {
        return new EducationInstitutionAssociatedConverters();
    }

    public class EducationInstitutionAssociatedConverters {
        public EducationInstitutionAssociatedConverters() {}

        public InstitutionEventDto convertInstitutionEventToDto(InstitutionEvent institutionEvent) {
            return modelMapper.map(institutionEvent, InstitutionEventDto.class);
        }

        public InstitutionEvent convertInstitutionEventDtoToEntity(InstitutionEventDto institutionEventDto) {
            return modelMapper.map(institutionEventDto, InstitutionEvent.class);
        }

        public AdminDto convertAdminToDto(Admin admin) {
            return adminService.convertToDto(admin);
        }

        public Admin convertAdminDtoToEntity(AdminDto adminDto) {
            return adminService.convertToEntity(adminDto);
        }
    }
}
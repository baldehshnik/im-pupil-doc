package im.pupil.api.service;

import im.pupil.api.dto.PupilDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.pupil.PupilNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.EducationalInstitution;
import im.pupil.api.model.Pupil;
import im.pupil.api.model.User;
import im.pupil.api.repository.PupilRepository;
import im.pupil.api.security.RolesEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PupilService {
    private final PupilRepository pupilRepository;
    private final EducationalInstitutionService educationalInstitutionService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    @Autowired
    public PupilService(PupilRepository pupilRepository,
                        EducationalInstitutionService educationalInstitutionService,
                        ModelMapper modelMapper, UserService userService, RoleService roleService, UserRoleService userRoleService) {
        this.pupilRepository = pupilRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    public Pupil findById(Integer id) {
        Optional<Pupil> pupil = pupilRepository.findById(id);

        return pupil.orElseThrow(() -> new PupilNotFoundException("No pupil with id " + id));
    }

    public Pupil findByEmail(String email) {
        User user = userService.findByEmail(email);
        Integer idOfRole = roleService.findByRoleName(RolesEnum.USER.getDescription()).getId();
        boolean isPupilHaveUserRole = userRoleService.existsByUserIdAndRoleId(user.getId(), idOfRole);

        if (!isPupilHaveUserRole) {
            throw new AdminNotFoundException("No pupil found with email: " + email);
        }

        return findPupilByUserId(user.getId());
    }

    public Pupil findPupilByUserId(Integer userId) {
        Optional<Pupil> pupil = pupilRepository.findByUserId(userId);

        return pupil.orElseThrow(() -> new PupilNotFoundException("No pupil found with userId: " + userId));
    }

    public Pupil findByCode(String code) {
        Optional<Pupil> pupil = pupilRepository.findByCode(code);

        return pupil.orElseThrow(() -> new PupilNotFoundException("No pupil with code " + code));
    }

    public List<Pupil> findByInstitutionId(Integer institutionId) {
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(institutionId);
        List<Pupil> pupilList = pupilRepository.findByInstitution_Id(educationalInstitution.getId());

        if (pupilList.isEmpty()) {
            throw new PupilNotFoundException("No pupil in institution with id: " + institutionId);
        }

        return pupilList;
    }

    public PupilDto convertToDto(Pupil pupil) {
        return modelMapper.map(pupil, PupilDto.class);
    }

    public Pupil convertToEntity(PupilDto pupilDto) {
        return modelMapper.map(pupilDto, Pupil.class);
    }
}

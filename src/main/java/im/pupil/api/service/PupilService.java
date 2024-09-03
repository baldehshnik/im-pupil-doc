package im.pupil.api.service;

import im.pupil.api.dto.PupilDto;
import im.pupil.api.exception.pupil.PupilNotFoundException;
import im.pupil.api.model.EducationalInstitution;
import im.pupil.api.model.Pupil;
import im.pupil.api.repository.PupilRepository;
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

    @Autowired
    public PupilService(PupilRepository pupilRepository,
                        EducationalInstitutionService educationalInstitutionService,
                        ModelMapper modelMapper) {
        this.pupilRepository = pupilRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.modelMapper = modelMapper;
    }

    public Pupil findById(Integer id) {
        Optional<Pupil> pupil = pupilRepository.findById(id);

        return pupil.orElseThrow(() -> new PupilNotFoundException("No pupil with id " + id));
    }

    public Pupil findByEmail(String email) {
        Optional<Pupil> pupil = pupilRepository.findByEmail(email);

        return pupil.orElseThrow(() -> new PupilNotFoundException("No pupil with email " + email));
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

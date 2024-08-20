package im.pupil.api.service;

import im.pupil.api.dto.InstitutionEventDto;
import im.pupil.api.model.InstitutionEvent;
import im.pupil.api.repository.EducationalInstitutionRepository;
import im.pupil.api.repository.InstitutionEventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EducationInstitutionService {

    EducationalInstitutionRepository educationalInstitutionRepository;
    InstitutionEventRepository institutionEventRepository;
    ModelMapper modelMapper;

    @Autowired
    public EducationInstitutionService(EducationalInstitutionRepository educationalInstitutionRepository,
                                       InstitutionEventRepository institutionEventRepository,
                                       ModelMapper modelMapper) {
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.institutionEventRepository = institutionEventRepository;
        this.modelMapper = modelMapper;
    }

    public List<InstitutionEvent> getInstitutionEventsOfEducationInstitutionById(Integer institutionId) {
        return institutionEventRepository.findByInstitutionId(institutionId);
    }

    public List<InstitutionEvent> getInstitutionEventsOfEducationInstitutionByAbbreviation(
            String institutionAbbreviation) {
        return institutionEventRepository
                .findByInstitutionId(
                        educationalInstitutionRepository.findByAbbreviation(institutionAbbreviation).getId()
                );
    }

    public List<InstitutionEvent> getInstitutionEventsOfEducationInstitutionByName(
            String institutionName) {
        return institutionEventRepository
                .findByInstitutionId(
                        educationalInstitutionRepository.findByName(institutionName).getId()
                );
    }

    public InstitutionEventDto convertToDto(InstitutionEvent institutionEvent) {
        return modelMapper.map(institutionEvent, InstitutionEventDto.class);
    }

    public InstitutionEvent convertToEvent(InstitutionEventDto institutionEventDto) {
        return modelMapper.map(institutionEventDto, InstitutionEvent.class);
    }
}
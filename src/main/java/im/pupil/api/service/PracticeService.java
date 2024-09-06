package im.pupil.api.service;

import im.pupil.api.dto.PracticeDto;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.model.EducationalInstitution;
import im.pupil.api.model.Practice;
import im.pupil.api.repository.PracticeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final EducationalInstitutionService educationalInstitutionService;
    private final ModelMapper modelMapper;

    @Autowired
    public PracticeService(PracticeRepository practiceRepository,
                           EducationalInstitutionService educationalInstitutionService,
                           ModelMapper modelMapper) {
        this.practiceRepository = practiceRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.modelMapper = modelMapper;
    }

    public Practice findPracticeById(Integer id) {
        Optional<Practice> practice = practiceRepository.findById(id);

        return practice.orElseThrow(() -> new PracticeNotFoundException("Practice not found with id: " + id));
    }

    public List<Practice> findPracticesByInstitutionId(Integer id) {
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(id);
        List<Practice> practices = practiceRepository.findByInstitution_Id(educationalInstitution.getId());

        if (practices.isEmpty()) {
            throw new PracticeNotFoundException("Not found practice associated with institution with id: " + id);
        }

        return practices;
    }

    public PracticeDto convertToDto(Practice practice){
        return modelMapper.map(practice, PracticeDto.class);
    }

    public Practice convertToEntity(PracticeDto practiceDto){
        return modelMapper.map(practiceDto, Practice.class);
    }
}

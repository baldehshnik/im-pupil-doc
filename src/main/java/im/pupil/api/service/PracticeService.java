package im.pupil.api.service;

import im.pupil.api.dto.PracticeDto;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.model.EducationalInstitution;
import im.pupil.api.model.InformationBlock;
import im.pupil.api.model.Practice;
import im.pupil.api.model.Relocation;
import im.pupil.api.repository.PracticeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PracticeService {
    private final PracticeRepository practiceRepository;
    private final EducationalInstitutionService educationalInstitutionService;
    private final ModelMapper modelMapper;
    private final InformationBlockService informationBlockService;
    private final RelocationService relocationService;

    @Autowired
    public PracticeService(PracticeRepository practiceRepository,
                           EducationalInstitutionService educationalInstitutionService,
                           InformationBlockService informationBlockService,
                           RelocationService relocationService,
                           ModelMapper modelMapper) {
        this.practiceRepository = practiceRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.modelMapper = modelMapper;
        this.informationBlockService = informationBlockService;
        this.relocationService = relocationService;
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

    @Transactional
    public void createPracticeWithRelocationInformationBlockWithExistingEducationInstitution(
            Practice practice,
            Set<Relocation> relocations,
            Set<InformationBlock> informationBlocks) {
        EducationalInstitution educationalInstitution = educationalInstitutionService
                .findEducationalInstitutionByAbbreviation(practice.getInstitution().getAbbreviation());
        practice.setInstitution(educationalInstitution);
        practiceRepository.save(practice);

        relocations.forEach(
                (relocationEntity) -> relocationService.saveRelocationWithPracticeId(relocationEntity, practice.getId())
        );

        informationBlocks.forEach(
                (informationBlockEntity) -> informationBlockService.saveInformationBlockWithPracticeId(informationBlockEntity, practice.getId())
        );
    }

    public PracticeDto convertToDto(Practice practice){
        return modelMapper.map(practice, PracticeDto.class);
    }

    public Practice convertToEntity(PracticeDto practiceDto){
        return modelMapper.map(practiceDto, Practice.class);
    }
}

package im.pupil.api.service;

import im.pupil.api.dto.information_block.UpdateInformationBlock;
import im.pupil.api.dto.practice.*;
import im.pupil.api.dto.relocation.UpdateRelocationDto;
import im.pupil.api.exception.practice.PracticeNotFoundException;
import im.pupil.api.model.*;
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

    private final AdminService adminService;
    private final EducationalInstitutionService educationalInstitutionService;

    private final ModelMapper modelMapper;
    private final InformationBlockService informationBlockService;
    private final RelocationService relocationService;

    @Autowired
    public PracticeService(
            PracticeRepository practiceRepository,
            EducationalInstitutionService educationalInstitutionService,
            InformationBlockService informationBlockService,
            RelocationService relocationService,
            ModelMapper modelMapper,
            AdminService adminService
    ) {
        this.practiceRepository = practiceRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.modelMapper = modelMapper;
        this.informationBlockService = informationBlockService;
        this.relocationService = relocationService;
        this.adminService = adminService;
    }

    @Transactional(readOnly = true)
    public Practice findPracticeById(Integer id) {
        Optional<Practice> practice = practiceRepository.findById(id);
        return practice.orElseThrow(() -> new PracticeNotFoundException("Practice not found with id: " + id));
    }

    @Transactional
    public void deletePractice(Integer id) {
        Optional<Practice> practice = practiceRepository.findById(id);
        if (practice.isEmpty()) throw new PracticeNotFoundException("Practice not found with id: " + id);

        practiceRepository.delete(practice.get());
    }

    @Transactional(readOnly = true)
    public List<Practice> findPracticesByInstitutionId(Integer id) {
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(id);
        return practiceRepository.findByInstitution_Id(educationalInstitution.getId());
    }

    @Transactional
    public void createPracticeWithRelocationInformationBlockWithExistingEducationInstitution(
            String email,
            CreatePracticeDto practiceDto,
            Set<Relocation> relocations,
            Set<InformationBlock> informationBlocks
    ) {
        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(admin.getInstitution().getId());
        Practice practice = convertToEntity(practiceDto);
        practice.setInstitution(educationalInstitution);
        practiceRepository.save(practice);

        relocations.forEach(
                (relocationEntity) -> relocationService.saveRelocationWithPracticeId(relocationEntity, practice.getId())
        );
        informationBlocks.forEach(
                (informationBlockEntity) -> informationBlockService.saveInformationBlockWithPracticeId(informationBlockEntity, practice.getId())
        );
    }

    @Transactional
    public void updatePracticeWithRelocationInformationBlockWithExistingEducationInstitution(
            String email,
            UpdatePracticeDto practiceDto,
            Set<UpdateRelocationDto> relocations,
            Set<UpdateInformationBlock> informationBlocks
    ) {
        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(admin.getInstitution().getId());
        Practice practice = convertToEntity(practiceDto);
        practice.setInstitution(educationalInstitution);
        practiceRepository.save(practice);

        relocationService.updateRelocationWithPracticeId(relocations, practice.getId());
        informationBlockService.updateInformationBlockWithPracticeId(informationBlocks, practice.getId());
    }

    public GetListPracticeDto convertToListDto(Practice practice) {
        return modelMapper.map(practice, GetListPracticeDto.class);
    }

    public GetPracticeDto convertToPracticeDto(Practice practice) {
        return modelMapper.map(practice, GetPracticeDto.class);
    }

    public Practice convertToEntity(UpdatePracticeDto practiceDto) {
        return modelMapper.map(practiceDto, Practice.class);
    }

    public Practice convertToEntity(CreatePracticeDto practiceDto) {
        return modelMapper.map(practiceDto, Practice.class);
    }
}
















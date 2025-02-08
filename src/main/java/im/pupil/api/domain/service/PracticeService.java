package im.pupil.api.domain.service;

import im.pupil.api.data.entity.*;
import im.pupil.api.data.entity.institution.EducationalInstitution;
import im.pupil.api.data.repository.PracticeRepository;
import im.pupil.api.data.repository.PupilRepository;
import im.pupil.api.domain.dto.information_block.UpdateInformationBlock;
import im.pupil.api.domain.dto.practice.CreatePracticeDto;
import im.pupil.api.domain.dto.practice.GetListPracticeDto;
import im.pupil.api.domain.dto.practice.GetPracticeDto;
import im.pupil.api.domain.dto.practice.UpdatePracticeDto;
import im.pupil.api.domain.dto.relocation.UpdateRelocationDto;
import im.pupil.api.domain.exception.UnexpectedException;
import im.pupil.api.domain.exception.practice.PracticeNotFoundException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import im.pupil.api.domain.image.storage.ImageWorker;
import im.pupil.api.domain.service.auth.AdminService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PracticeService {

    private final PracticeRepository practiceRepository;
    private final PupilRepository pupilRepository;

    private final AdminService adminService;
    private final EducationalInstitutionService educationalInstitutionService;
    private final InformationBlockService informationBlockService;
    private final RelocationService relocationService;

    private final ModelMapper modelMapper;

    private final ImageWorker imageWorker;

    public PracticeService(
            PracticeRepository practiceRepository,
            PupilRepository pupilRepository,
            EducationalInstitutionService educationalInstitutionService,
            InformationBlockService informationBlockService,
            RelocationService relocationService,
            ModelMapper modelMapper,
            AdminService adminService,
            ImageWorker imageWorker
    ) {
        this.practiceRepository = practiceRepository;
        this.pupilRepository = pupilRepository;
        this.educationalInstitutionService = educationalInstitutionService;
        this.modelMapper = modelMapper;
        this.informationBlockService = informationBlockService;
        this.relocationService = relocationService;
        this.adminService = adminService;
        this.imageWorker = imageWorker;
    }

    @Transactional(readOnly = true)
    public List<GetListPracticeDto> readPracticeListFoPupil(
            String email
    ) throws UnexpectedException {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            List<Practice> practices = practiceRepository.findByInstitution_Id(pupil.getInstitution().getId());
            return practices.stream()
                    .map(m -> modelMapper.map(m, GetListPracticeDto.class))
                    .toList();
        } catch (PupilNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public GetPracticeDto findPracticeById(Integer id) {
        Optional<Practice> practice = practiceRepository.findById(id);
        return practice.map(m -> modelMapper.map(m, GetPracticeDto.class))
                .orElseThrow(() -> new PracticeNotFoundException("Practice not found with id: " + id));
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
            Set<InformationBlock> informationBlocks,
            @NotNull MultipartFile image
    ) throws UnexpectedException {
        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(admin.getInstitution().getId());
        Practice practice = convertToEntity(practiceDto);

        String url = imageWorker.saveImage(image, ImageWorker.ImageType.PRACTICE);
        practice.setIcon(url);

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
            Set<UpdateInformationBlock> informationBlocks,
            @Nullable MultipartFile image
    ) throws UnexpectedException {
        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = educationalInstitutionService.findEducationalInstitutionById(admin.getInstitution().getId());
        Practice practice = convertToEntity(practiceDto);

        if (image != null && !image.isEmpty()) {
            String url = imageWorker.saveImage(image, ImageWorker.ImageType.PRACTICE);
            practice.setIcon(url);
        }

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
















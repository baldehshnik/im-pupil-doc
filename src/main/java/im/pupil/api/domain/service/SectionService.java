package im.pupil.api.domain.service;

import im.pupil.api.data.entity.Admin;
import im.pupil.api.data.entity.Pupil;
import im.pupil.api.data.entity.Section;
import im.pupil.api.data.repository.PupilRepository;
import im.pupil.api.data.repository.SectionRepository;
import im.pupil.api.domain.dto.section.CreateSectionDto;
import im.pupil.api.domain.dto.section.GetSectionDto;
import im.pupil.api.domain.dto.section.UpdateSectionDto;
import im.pupil.api.domain.exception.UnexpectedException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import im.pupil.api.domain.exception.section.SectionNotFoundException;
import im.pupil.api.domain.image.storage.ImageWorker;
import im.pupil.api.domain.service.auth.AdminService;
import jakarta.annotation.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final PupilRepository pupilRepository;

    private final AdminService adminService;

    private final ModelMapper modelMapper;
    private final ImageWorker imageWorker;

    public SectionService(
            SectionRepository sectionRepository,
            PupilRepository pupilRepository,
            AdminService adminService,
            ModelMapper modelMapper,
            ImageWorker imageWorker
    ) {
        this.sectionRepository = sectionRepository;
        this.pupilRepository = pupilRepository;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
        this.imageWorker = imageWorker;
    }

    @Transactional(readOnly = true)
    public List<GetSectionDto> readSectionsForPupil(String email) throws PupilNotFoundException, UnexpectedException {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            List<Section> sections = sectionRepository.readSections(pupil.getInstitution().getId());
            return sections.stream()
                    .map(m -> modelMapper.map(m, GetSectionDto.class))
                    .toList();
        } catch (PupilNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    @Transactional(readOnly = true)
    public List<GetSectionDto> readSections(
            Integer institutionId
    ) {
        List<Section> sections = sectionRepository.readSections(institutionId);
        return sections.stream()
                .map(m -> modelMapper.map(m, GetSectionDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public GetSectionDto findSectionById(Integer id) {
        Optional<Section> optionalGetSectionDto = sectionRepository.findById(id);
        if (optionalGetSectionDto.isEmpty()) throw new SectionNotFoundException();

        return modelMapper.map(optionalGetSectionDto.get(), GetSectionDto.class);
    }

    @Transactional
    public void deleteSectionById(Integer id) {
        Optional<Section> optionalGetSectionDto = sectionRepository.findById(id);
        if (optionalGetSectionDto.isEmpty()) throw new SectionNotFoundException();
        sectionRepository.delete(optionalGetSectionDto.get());
    }

    @Transactional
    public void createSection(
            String email,
            CreateSectionDto createSectionDto,
            MultipartFile image
    ) throws UnexpectedException {
        Admin admin = adminService.findAdminByEmail(email);
        Section section = modelMapper.map(createSectionDto, Section.class);
        section.setInstitution(admin.getInstitution());

        String url = imageWorker.saveImage(image, ImageWorker.ImageType.SECTION);
        section.setIcon(url);

        sectionRepository.save(section);
    }

    @Transactional
    public void updateSection(
            String email,
            UpdateSectionDto updateSectionDto,
            @Nullable MultipartFile image
    ) throws UnexpectedException {
        Admin admin = adminService.findAdminByEmail(email);
        Section section = modelMapper.map(updateSectionDto, Section.class);
        section.setInstitution(admin.getInstitution());

        if (image != null && !image.isEmpty()) {
            String url = imageWorker.saveImage(image, ImageWorker.ImageType.SECTION);
            section.setIcon(url);
        }

        sectionRepository.save(section);
    }
}





























package im.pupil.api.service;

import im.pupil.api.dto.section.CreateSectionDto;
import im.pupil.api.dto.section.GetSectionDto;
import im.pupil.api.dto.section.UpdateSectionDto;
import im.pupil.api.exception.section.SectionNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.Section;
import im.pupil.api.repository.SectionRepository;
import im.pupil.api.service.auth.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    private final AdminService adminService;

    private final ModelMapper modelMapper;

    public SectionService(
            SectionRepository sectionRepository,
            AdminService adminService,
            ModelMapper modelMapper
    ) {
        this.sectionRepository = sectionRepository;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
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
            CreateSectionDto createSectionDto
    ) {
        Admin admin = adminService.findAdminByEmail(email);
        Section section = modelMapper.map(createSectionDto, Section.class);
        section.setInstitution(admin.getInstitution());

        sectionRepository.save(section);
    }

    @Transactional
    public void updateSection(
            String email,
            UpdateSectionDto updateSectionDto
    ) {
        Admin admin = adminService.findAdminByEmail(email);
        Section section = modelMapper.map(updateSectionDto, Section.class);
        section.setInstitution(admin.getInstitution());

        sectionRepository.save(section);
    }
}





























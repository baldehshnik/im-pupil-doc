package im.pupil.api.service;

import im.pupil.api.domain.image.storage.ImageWorker;
import im.pupil.api.dto.about.GetAboutDto;
import im.pupil.api.model.Admin;
import im.pupil.api.model.institution.EducationalInstitution;
import im.pupil.api.model.about.About;
import im.pupil.api.repository.AboutRepository;
import im.pupil.api.service.auth.AdminService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.Optional;

@Service
public class AboutService {

    private final AboutRepository aboutRepository;

    private final ImageWorker imageWorker;

    private final AdminService adminService;

    private final ModelMapper modelMapper;

    public AboutService(
            AboutRepository aboutRepository,
            ImageWorker imageWorker,
            AdminService adminService,
            ModelMapper modelMapper
    ) {
        this.aboutRepository = aboutRepository;
        this.imageWorker = imageWorker;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetAboutDto> readAboutBlocks(
            Integer institutionId
    ) {
        List<About> aboutBlocks = aboutRepository.readAboutBlocks(institutionId);
        return aboutBlocks.stream()
                .map(m -> modelMapper.map(m, GetAboutDto.class))
                .toList();
    }

    @Transactional
    public void updateAboutBlock(
            @NotNull String email,
            @Nullable Integer id,
            @Nullable String description,
            @Nullable MultipartFile image
    ) throws UnexpectedException {
        if (id == null && description == null && (image == null || image.isEmpty())) return;

        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = admin.getInstitution();

        String imageUrl;
        if (image == null || image.isEmpty()) imageUrl = null;
        else imageUrl = imageWorker.saveImage(image, ImageWorker.ImageType.ABOUT);

        if (id != null) {
            updateAboutBlock(educationalInstitution, imageUrl, id, description);
        } else {
            saveNewAboutBlock(imageUrl, description, educationalInstitution);
        }
    }

    private void updateAboutBlock(
            @NotNull EducationalInstitution educationalInstitution,
            String imageUrl,
            Integer id,
            String description
    ) {
        Optional<About> optionalAbout = aboutRepository.findById(id);
        if (optionalAbout.isEmpty()) {
            saveNewAboutBlock(imageUrl, description, educationalInstitution);
            return;
        }

        About about = optionalAbout.get();
        about.setIcon(imageUrl);
        about.setDescription(description);
        aboutRepository.save(about);
    }

    private void saveNewAboutBlock(
            String imageUrl,
            String description,
            @NotNull EducationalInstitution educationalInstitution
    ) {
        About about = new About();
        about.setIcon(imageUrl);
        about.setDescription(description);
        about.setInstitution(educationalInstitution);
        aboutRepository.save(about);
    }
}




























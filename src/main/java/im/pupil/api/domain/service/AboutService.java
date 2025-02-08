package im.pupil.api.domain.service;

import im.pupil.api.data.entity.Admin;
import im.pupil.api.data.entity.Pupil;
import im.pupil.api.data.entity.about.About;
import im.pupil.api.data.entity.institution.EducationalInstitution;
import im.pupil.api.data.repository.AboutRepository;
import im.pupil.api.data.repository.PupilRepository;
import im.pupil.api.domain.dto.about.GetAboutDto;
import im.pupil.api.domain.exception.UnexpectedException;
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

@Service
public class AboutService {

    private final AboutRepository aboutRepository;
    private final PupilRepository pupilRepository;

    private final ImageWorker imageWorker;

    private final AdminService adminService;

    private final ModelMapper modelMapper;

    public AboutService(
            AboutRepository aboutRepository,
            PupilRepository pupilRepository,
            ImageWorker imageWorker,
            AdminService adminService,
            ModelMapper modelMapper
    ) {
        this.aboutRepository = aboutRepository;
        this.pupilRepository = pupilRepository;
        this.imageWorker = imageWorker;
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetAboutDto> readAboutBlocks(String email) throws PupilNotFoundException, UnexpectedException {
        try {
            Optional<Pupil> optionalPupil = pupilRepository.findByEmail(email);
            if (optionalPupil.isEmpty()) throw new PupilNotFoundException();

            Pupil pupil = optionalPupil.get();
            List<About> aboutBlocks = aboutRepository.readAboutBlocks(pupil.getInstitution().getId());
            return aboutBlocks.stream()
                    .map(m -> modelMapper.map(m, GetAboutDto.class))
                    .toList();
        } catch (PupilNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
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
            @Nullable String icon,
            @Nullable MultipartFile image
    ) throws UnexpectedException {
        if (id == null && description == null && icon == null && (image == null || image.isEmpty())) return;

        Admin admin = adminService.findAdminByEmail(email);
        EducationalInstitution educationalInstitution = admin.getInstitution();

        System.out.println(description);
        System.out.println(icon);

        String imageUrl;
        if (image == null || image.isEmpty()) imageUrl = icon;
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

        if (imageUrl == null) about.setIcon(null);
        else about.setIcon(imageUrl.replaceAll("\"", ""));

        if (description == null) about.setDescription(null);
        else about.setDescription(description.replaceAll("\"", ""));

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




























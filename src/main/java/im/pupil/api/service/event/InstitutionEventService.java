package im.pupil.api.service.event;

import im.pupil.api.domain.image.storage.ImageWorker;
import im.pupil.api.dto.event.AddInstitutionEventDto;
import im.pupil.api.dto.event.GetInstitutionEventDto;
import im.pupil.api.dto.event.UpdateInstitutionEventDto;
import im.pupil.api.exception.admin.AdminNotFoundException;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.event.InstitutionEventNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.InstitutionEvent;
import im.pupil.api.model.institution.EducationalInstitution;
import im.pupil.api.repository.AdminRepository;
import im.pupil.api.repository.EducationalInstitutionRepository;
import im.pupil.api.repository.InstitutionEventRepository;
import jakarta.annotation.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionEventService {

    private final InstitutionEventRepository institutionEventRepository;
    private final AdminRepository adminRepository;
    private final EducationalInstitutionRepository educationalInstitutionRepository;

    private final ModelMapper modelMapper;
    private final ImageWorker imageWorker;

    public InstitutionEventService(
            InstitutionEventRepository institutionEventRepository,
            AdminRepository adminRepository,
            EducationalInstitutionRepository educationalInstitutionRepository,
            ModelMapper modelMapper,
            ImageWorker imageWorker
    ) {
        this.institutionEventRepository = institutionEventRepository;
        this.adminRepository = adminRepository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.modelMapper = modelMapper;
        this.imageWorker = imageWorker;
    }

    @Transactional
    public void deleteInstitutionEvent(
            Integer id
    ) {
        Optional<InstitutionEvent> optionalInstitutionEvent = institutionEventRepository.findById(id);
        if (optionalInstitutionEvent.isEmpty()) throw new InstitutionEventNotFoundException();

        institutionEventRepository.delete(optionalInstitutionEvent.get());
    }

    @Transactional(readOnly = true)
    public GetInstitutionEventDto readInstitutionEvent(
            Integer id
    ) {
        Optional<InstitutionEvent> optionalInstitutionEvent = institutionEventRepository.findById(id);
        if (optionalInstitutionEvent.isEmpty()) throw new InstitutionEventNotFoundException();

        return modelMapper.map(optionalInstitutionEvent.get(), GetInstitutionEventDto.class);
    }

    @Transactional(readOnly = true)
    public List<GetInstitutionEventDto> readInstitutionEvents(
            Integer institutionId
    ) {
        Optional<EducationalInstitution> optionalEducationalInstitution = educationalInstitutionRepository.findById(institutionId);
        if (optionalEducationalInstitution.isEmpty()) throw new EducationalInstitutionNotFoundException();

        List<InstitutionEvent> institutionEvents = institutionEventRepository.findByInstitutionId(optionalEducationalInstitution.get().getId());
        return institutionEvents.stream()
                .map(m -> modelMapper.map(m, GetInstitutionEventDto.class))
                .toList();
    }

    @Transactional
    public void createNewEvent(
            String email,
            AddInstitutionEventDto addInstitutionEventDto,
            MultipartFile image
    ) throws UnexpectedException {
        Optional<Admin> optionalAdmin = adminRepository.findRegisteredAdminByEmail(email);
        if (optionalAdmin.isEmpty()) throw new AdminNotFoundException();

        InstitutionEvent institutionEvent = new InstitutionEvent();
        institutionEvent.setTitle(addInstitutionEventDto.getTitle());
        institutionEvent.setDescription(addInstitutionEventDto.getDescription());
        institutionEvent.setDuration(addInstitutionEventDto.getDuration());
        institutionEvent.setType(addInstitutionEventDto.getType());
        institutionEvent.setInstitution(optionalAdmin.get().getInstitution());

        String url = imageWorker.saveImage(image, ImageWorker.ImageType.EVENT);
        institutionEvent.setImage(url);

        institutionEventRepository.save(institutionEvent);
    }

    @Transactional
    public void updateEvent(
            UpdateInstitutionEventDto updateInstitutionEventDto,
            @Nullable MultipartFile image
    ) throws UnexpectedException {
        Optional<InstitutionEvent> optionalInstitutionEvent = institutionEventRepository.findById(updateInstitutionEventDto.getId());
        if (optionalInstitutionEvent.isEmpty()) throw new InstitutionEventNotFoundException();

        InstitutionEvent institutionEvent = modelMapper.map(updateInstitutionEventDto, InstitutionEvent.class);
        institutionEvent.setInstitution(optionalInstitutionEvent.get().getInstitution());
        if (image != null && !image.isEmpty()) {
            String url = imageWorker.saveImage(image, ImageWorker.ImageType.EVENT);
            institutionEvent.setImage(url);
        }

        institutionEventRepository.save(institutionEvent);
    }
}

























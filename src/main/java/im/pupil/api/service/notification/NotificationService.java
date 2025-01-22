package im.pupil.api.service.notification;

import im.pupil.api.dto.notification.GetNotificationDto;
import im.pupil.api.exception.notification.NotificationNotFoundException;
import im.pupil.api.model.Admin;
import im.pupil.api.model.Notification;
import im.pupil.api.model.institution.EducationalInstitution;
import im.pupil.api.repository.AdminRepository;
import im.pupil.api.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AdminRepository adminRepository;

    private final ModelMapper modelMapper;

    public NotificationService(
            NotificationRepository notificationRepository,
            AdminRepository adminRepository,
            ModelMapper modelMapper
    ) {
        this.notificationRepository = notificationRepository;
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<GetNotificationDto> readAdminNotifications(
            String email
    ) {
        List<Notification> notifications = notificationRepository.readNotificationsOfAdmin(email);
        return notifications.stream()
                .map(m -> modelMapper.map(m, GetNotificationDto.class))
                .toList();
    }

    @Transactional
    public void updateNotificationStatus(
            String email,
            Integer id
    ) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isEmpty()) throw new NotificationNotFoundException();

        Notification notification = optionalNotification.get();
        notification.setStatus(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void addNewAdminNotification(
            String firstname,
            String lastname,
            EducationalInstitution educationalInstitution
    ) {
        List<Admin> admins = adminRepository.findByInstitutionIdAndTeacherAccess(educationalInstitution.getId());
        admins.forEach(m -> {
            Notification notification = new Notification();
            notification.setIcon(NotificationIconsProvider.NEW_ADMIN.getLink());
            notification.setTitle(NotificationMessagesProvider.NEW_ADMIN_TITLE.getMessage());
            notification.setDescription(NotificationMessagesProvider.NEW_ADMIN_DESCRIPTION.getMessage() + lastname + " " + firstname);
            notification.setInstitution(educationalInstitution);
            notification.setDateTime(Instant.now());
            notification.setType(1);
            notification.setAdmin(m);
            notificationRepository.save(notification);
        });
    }

    @Transactional
    public void addNewPupilNotification(
            String code,
            EducationalInstitution educationalInstitution
    ) {
        List<Admin> admins = adminRepository.findByInstitutionIdAndTeacherAccess(educationalInstitution.getId());
        admins.forEach(m -> {
            Notification notification = new Notification();
            notification.setIcon(NotificationIconsProvider.NEW_STUDENT.getLink());
            notification.setTitle(NotificationMessagesProvider.NEW_STUDENT_TITLE.getMessage());
            notification.setDescription(NotificationMessagesProvider.NEW_STUDENT_DESCRIPTION.getMessage() + code);
            notification.setInstitution(educationalInstitution);
            notification.setDateTime(Instant.now());
            notification.setType(2);
            notification.setAdmin(m);
            notificationRepository.save(notification);
        });
    }
}






























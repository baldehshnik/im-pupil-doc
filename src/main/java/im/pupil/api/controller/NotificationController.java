package im.pupil.api.controller;

import im.pupil.api.dto.SuccessAnswer;
import im.pupil.api.dto.notification.GetNotificationDto;
import im.pupil.api.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetNotificationDto> readAdminNotifications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return notificationService.readAdminNotifications(email);
    }

    @PostMapping("/admin/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateAdminNotificationStatus(
            @RequestParam("notificationId") Integer id
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        notificationService.updateNotificationStatus(email, id);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success notification updating"));
    }
}






















package im.pupil.api.presentation.services;

import im.pupil.api.domain.dto.services.ReadServiceDto;
import im.pupil.api.domain.service.ApplicationServicesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServicesController {

    private final ApplicationServicesService applicationServicesService;

    public ServicesController(ApplicationServicesService applicationServicesService) {
        this.applicationServicesService = applicationServicesService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ReadServiceDto> readServices() {
        return applicationServicesService.readServices();
    }
}





























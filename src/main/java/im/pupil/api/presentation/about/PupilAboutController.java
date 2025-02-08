package im.pupil.api.presentation.about;

import im.pupil.api.domain.dto.about.GetAboutDto;
import im.pupil.api.domain.service.AboutService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pupil/about")
public class PupilAboutController {

    private final AboutService aboutService;

    public PupilAboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetAboutDto> readAboutBlocks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return aboutService.readAboutBlocks(email);
    }
}



























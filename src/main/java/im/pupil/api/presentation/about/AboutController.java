package im.pupil.api.presentation.about;

import im.pupil.api.domain.dto.SuccessAnswer;
import im.pupil.api.domain.dto.about.GetAboutDto;
import im.pupil.api.domain.service.AboutService;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;
import java.util.List;

@RestController
@RequestMapping("/education/about")
public class AboutController {

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<GetAboutDto> readAboutBlocks(
            @RequestParam("institutionId") Integer institutionId
    ) {
        return aboutService.readAboutBlocks(institutionId);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessAnswer> updateAboutBlock(
            @Nullable
            @RequestPart("aboutId")
            String aboutIdStr,

            @Nullable
            @RequestPart("description")
            String description,

            @Nullable
            @RequestPart("icon")
            String icon,

            @Nullable
            @RequestPart("image")
            MultipartFile image
    ) throws UnexpectedException {
        Integer aboutId = null;
        if (aboutIdStr != null && !aboutIdStr.isEmpty()) {
            try {
                aboutId = Integer.parseInt(aboutIdStr);
            } catch (NumberFormatException e) {
                throw new UnexpectedException("Invalid aboutId format: " + aboutIdStr, e);
            }
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        aboutService.updateAboutBlock(email, aboutId, description, icon, image);
        return ResponseEntity.ok(SuccessAnswer.createSuccessAnswer("Success about block updating!"));
    }
}
















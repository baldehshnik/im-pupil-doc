package im.pupil.api.presentation.pass;

import im.pupil.api.domain.dto.lesson.GetFullPassWithGroupMemberDto;
import im.pupil.api.domain.service.schedule.PassService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pupil/passes")
public class PupilPassController {

    private final PassService passService;

    public PupilPassController(PassService passService) {
        this.passService = passService;
    }

    @GetMapping("/group/month")
    @PreAuthorize("hasRole('USER')")
    public List<GetFullPassWithGroupMemberDto> readPassesOfGroupPerMonthByPrefect(
            @RequestParam("date") LocalDate date
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return passService.readPassesOfGroupPerMonthByPrefect(email, date);
    }
}

























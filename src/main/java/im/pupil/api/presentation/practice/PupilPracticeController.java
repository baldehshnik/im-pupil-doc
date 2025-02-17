package im.pupil.api.presentation.practice;

import im.pupil.api.domain.dto.practice.GetListPracticeDto;
import im.pupil.api.domain.dto.practice.GetPracticeDto;
import im.pupil.api.domain.service.PracticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pupil/practice")
public class PupilPracticeController {

    private final PracticeService practiceService;

    public PupilPracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<GetListPracticeDto> readPracticeList() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return practiceService.readPracticeListFoPupil(email);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public GetPracticeDto readPracticeById(@PathVariable Integer id) {
        return practiceService.findPracticeById(id);
    }
}


























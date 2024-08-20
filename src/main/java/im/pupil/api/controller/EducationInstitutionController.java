package im.pupil.api.controller;

import im.pupil.api.dto.InstitutionEventDto;
import im.pupil.api.service.EducationInstitutionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/education/institution")
public class EducationInstitutionController {
    private final EducationInstitutionService educationInstitutionService;

    @Autowired
    public EducationInstitutionController(EducationInstitutionService educationInstitutionService, ModelMapper modelMapper) {
        this.educationInstitutionService = educationInstitutionService;
    }

    @GetMapping("/events/id/{institutionId}")
    public List<InstitutionEventDto> getEventsByInstitutionId(@PathVariable Integer institutionId) {
        return educationInstitutionService
                .getInstitutionEventsOfEducationInstitutionById(institutionId)
                .stream()
                .map(educationInstitutionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/institutionName/{institutionName}")
    public List<InstitutionEventDto> getEventsByInstitutionName(@PathVariable String institutionName) {
        return educationInstitutionService
                .getInstitutionEventsOfEducationInstitutionByName(institutionName)
                .stream()
                .map(educationInstitutionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/instituteAbbreviation/{institutionAbbreviation}")
    public List<InstitutionEventDto> getEventsByInstitutionAbbreviation(@PathVariable String institutionAbbreviation) {
        return educationInstitutionService
                .getInstitutionEventsOfEducationInstitutionByAbbreviation(institutionAbbreviation)
                .stream()
                .map(educationInstitutionService::convertToDto)
                .collect(Collectors.toList());
    }
}
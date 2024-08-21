package im.pupil.api.controller;

import im.pupil.api.dto.InstitutionEventDto;
import im.pupil.api.service.EducationInstitutionService;
import im.pupil.api.service.EducationalInstitutionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/education/institution")
public class EducationalInstitutionController {
    private final EducationalInstitutionService educationalInstitutionService;

    @Autowired
    public EducationalInstitutionController(EducationalInstitutionService educationalInstitutionService, ModelMapper modelMapper) {
        this.educationalInstitutionService = educationalInstitutionService;
    }

    @GetMapping("/events/id/{institutionId}")
    public List<InstitutionEventDto> getEventsByInstitutionId(@PathVariable Integer institutionId) {
        return educationalInstitutionService
                .getInstitutionEventsOfEducationInstitutionById(institutionId)
                .stream()
                .map(educationalInstitutionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/institutionName/{institutionName}")
    public List<InstitutionEventDto> getEventsByInstitutionName(@PathVariable String institutionName) {
        return educationalInstitutionService
                .getInstitutionEventsOfEducationInstitutionByName(institutionName)
                .stream()
                .map(educationalInstitutionService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/instituteAbbreviation/{institutionAbbreviation}")
    public List<InstitutionEventDto> getEventsByInstitutionAbbreviation(@PathVariable String institutionAbbreviation) {
        return educationalInstitutionService
                .getInstitutionEventsOfEducationInstitutionByAbbreviation(institutionAbbreviation)
                .stream()
                .map(educationalInstitutionService::convertToDto)
                .collect(Collectors.toList());
    }
}
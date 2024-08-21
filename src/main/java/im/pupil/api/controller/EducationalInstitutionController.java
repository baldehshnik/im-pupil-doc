package im.pupil.api.controller;

import im.pupil.api.dto.InstitutionEventDto;
import im.pupil.api.exception.educational.institution.EducationalInstitutionNotFoundException;
import im.pupil.api.exception.educational.institution.response.EducationalInstitutionErrorResponse;
import im.pupil.api.exception.insitution.event.InstitutionEventNotFoundException;
import im.pupil.api.exception.insitution.event.response.InstitutionEventErrorResponse;
import im.pupil.api.service.EducationalInstitutionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler
    private ResponseEntity<InstitutionEventErrorResponse> handleNotFoundInstitutionEventException(
            InstitutionEventNotFoundException exception){
        InstitutionEventErrorResponse errorResponse = new InstitutionEventErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EducationalInstitutionErrorResponse> handleNotFoundEducationInstitutionException(
            EducationalInstitutionNotFoundException exception){
        EducationalInstitutionErrorResponse errorResponse = new EducationalInstitutionErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
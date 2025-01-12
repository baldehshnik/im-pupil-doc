package im.pupil.api.handler;

import im.pupil.api.exception.admin.NotEnoughAccessException;
import im.pupil.api.exception.admin.response.AdminErrorResponse;
import im.pupil.api.exception.exam.ExamAlreadyExistsException;
import im.pupil.api.exception.exam.ExamNotFoundException;
import im.pupil.api.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.exception.institution_group.GroupMemberWasAddedYearlyException;
import im.pupil.api.exception.institution_group.InstitutionGroupNotFoundException;
import im.pupil.api.exception.institution_group.InstitutionGroupWasAddedYearlyException;
import im.pupil.api.exception.schedule.*;
import im.pupil.api.exception.section.SectionNotFoundException;
import im.pupil.api.exception.speciality.SpecialityNotFoundException;
import im.pupil.api.exception.storage.FailedStorageConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleNotEnoughAccessException(NotEnoughAccessException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleInstitutionGroundNotFoundException(InstitutionGroupNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleGroupMemberWasAddedYearlyException(GroupMemberWasAddedYearlyException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleInstitutionGroupWasAddedYearlyException(InstitutionGroupWasAddedYearlyException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleSpecialityNotFoundException(SpecialityNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleGroupMemberNotFoundException(GroupMemberNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleSectionNotFoundException(SectionNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleFailedStorageConnectionException(FailedStorageConnectionException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleScheduleNotFoundException(ScheduleNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleLessonNotFoundException(LessonNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleScheduleWasNotStartedException(ScheduleWasNotStartedException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleScheduleIsNotActiveException(ScheduleIsNotActiveException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleGroupMemberAlreadyHasAPassException(GroupMemberAlreadyHasAPassException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleExamNotFoundException(ExamNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AdminErrorResponse> handleExamAlreadyExistsException(ExamAlreadyExistsException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
























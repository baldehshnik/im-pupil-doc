package im.pupil.api.handler;

import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import im.pupil.api.domain.exception.event.InstitutionEventNotFoundException;
import im.pupil.api.domain.exception.institution_group.GroupMemberNotFoundException;
import im.pupil.api.domain.exception.institution_group.InstitutionGroupNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InstitutionExceptionHandler {

    @ExceptionHandler(InstitutionGroupNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleInstitutionGroupNotFoundException(InstitutionGroupNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupMemberNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleGroupMemberNotFoundException(GroupMemberNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstitutionEventNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleInstitutionEventNotFoundException(InstitutionEventNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}





















package im.pupil.api.handler;

import im.pupil.api.domain.exception.admin.AdminNotFoundException;
import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import im.pupil.api.domain.exception.pupil.PupilAlreadyRegisteredException;
import im.pupil.api.domain.exception.pupil.PupilNotConfirmedYetException;
import im.pupil.api.domain.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    private ResponseEntity<AdminErrorResponse> handleAdminNotFoundException(AdminNotFoundException exception) {
        AdminErrorResponse response = new AdminErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PupilAlreadyRegisteredException.class)
    public ResponseEntity<AdminErrorResponse> handlePupilAlreadyRegisteredException(PupilAlreadyRegisteredException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PupilNotConfirmedYetException.class)
    public ResponseEntity<AdminErrorResponse> handlePupilNotConfirmedYetException(PupilNotConfirmedYetException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }
}



















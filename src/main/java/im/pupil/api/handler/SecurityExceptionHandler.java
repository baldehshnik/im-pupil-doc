package im.pupil.api.handler;

import im.pupil.api.domain.exception.admin.AdminNotConfirmedYetException;
import im.pupil.api.domain.exception.admin.NotEnoughAccessException;
import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(NotEnoughAccessException.class)
    public ResponseEntity<AdminErrorResponse> handleNotEnoughAccessException(NotEnoughAccessException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AdminNotConfirmedYetException.class)
    public ResponseEntity<AdminErrorResponse> handleAdminNotConfirmedYetException(AdminNotConfirmedYetException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }
}


































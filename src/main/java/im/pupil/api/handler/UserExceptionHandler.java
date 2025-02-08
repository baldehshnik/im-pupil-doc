package im.pupil.api.handler;

import im.pupil.api.domain.exception.ExceptionResponse;
import im.pupil.api.domain.exception.admin.AdminNotFoundException;
import im.pupil.api.domain.exception.pupil.PupilAlreadyRegisteredException;
import im.pupil.api.domain.exception.pupil.PupilNotConfirmedYetException;
import im.pupil.api.domain.exception.pupil.PupilNotFoundException;
import im.pupil.api.domain.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AdminNotFoundException.class)
    private ResponseEntity<ExceptionResponse> handleAdminNotFoundException(AdminNotFoundException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PupilNotFoundException.class)
    private ResponseEntity<ExceptionResponse> handlePupilNotFoundException(PupilNotFoundException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PupilAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> handlePupilAlreadyRegisteredException(PupilAlreadyRegisteredException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.CONFLICT.value(), exception.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(PupilNotConfirmedYetException.class)
    public ResponseEntity<ExceptionResponse> handlePupilNotConfirmedYetException(PupilNotConfirmedYetException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.CONFLICT.value(), exception.getMessage()),
                HttpStatus.CONFLICT
        );
    }
}



















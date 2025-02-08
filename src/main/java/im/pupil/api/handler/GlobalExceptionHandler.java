package im.pupil.api.handler;

import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import im.pupil.api.domain.exception.parsing.DataParsingException;
import im.pupil.api.domain.exception.storage.FailedStorageConnectionException;
import im.pupil.api.domain.exception.user.IncorrectDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataParsingException.class)
    public ResponseEntity<AdminErrorResponse> handleDataParsingException(DataParsingException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedStorageConnectionException.class)
    public ResponseEntity<AdminErrorResponse> handleFailedStorageConnectionException(FailedStorageConnectionException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<AdminErrorResponse> handleIncorrectDataException(IncorrectDataException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }
}
























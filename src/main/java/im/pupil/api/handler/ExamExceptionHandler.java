package im.pupil.api.handler;

import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import im.pupil.api.domain.exception.exam.ExamAlreadyExistsException;
import im.pupil.api.domain.exception.exam.ExamNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExamExceptionHandler {

    @ExceptionHandler(ExamNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleExamNotFoundException(ExamNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExamAlreadyExistsException.class)
    public ResponseEntity<AdminErrorResponse> handleExamAlreadyExistsException(ExamAlreadyExistsException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }
}























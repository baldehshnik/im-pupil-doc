package im.pupil.api.handler;

import im.pupil.api.domain.exception.admin.response.AdminErrorResponse;
import im.pupil.api.domain.exception.schedule.LessonNotFoundException;
import im.pupil.api.domain.exception.schedule.ScheduleIsNotActiveException;
import im.pupil.api.domain.exception.schedule.ScheduleNotFoundException;
import im.pupil.api.domain.exception.schedule.ScheduleWasNotStartedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScheduleExceptionHandler {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleScheduleNotFoundException(ScheduleNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LessonNotFoundException.class)
    public ResponseEntity<AdminErrorResponse> handleLessonNotFoundException(LessonNotFoundException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ScheduleWasNotStartedException.class)
    public ResponseEntity<AdminErrorResponse> handleScheduleWasNotStartedException(ScheduleWasNotStartedException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ScheduleIsNotActiveException.class)
    public ResponseEntity<AdminErrorResponse> handleScheduleIsNotActiveException(ScheduleIsNotActiveException exception) {
        return new ResponseEntity<>(new AdminErrorResponse(exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}

















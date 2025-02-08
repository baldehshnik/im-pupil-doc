package im.pupil.api.handler;

import im.pupil.api.domain.exception.information.block.InformationBlockAlreadyExistsException;
import im.pupil.api.domain.exception.information.block.response.InformationBlockErrorResponse;
import im.pupil.api.domain.exception.practice.PracticeNotCreatedException;
import im.pupil.api.domain.exception.practice.PracticeNotFoundException;
import im.pupil.api.domain.exception.practice.response.PracticeErrorResponse;
import im.pupil.api.domain.exception.relocation.RelocationAlreadyExistsException;
import im.pupil.api.domain.exception.relocation.response.RelocationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PracticeExceptionHandler {

    @ExceptionHandler(PracticeNotFoundException.class)
    private ResponseEntity<PracticeErrorResponse> handlePracticeNotFoundException(PracticeNotFoundException exception) {
        return new ResponseEntity<>(new PracticeErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PracticeNotCreatedException.class)
    private ResponseEntity<PracticeErrorResponse> handlePracticeNotCreatedException(PracticeNotCreatedException exception) {
        return new ResponseEntity<>(new PracticeErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RelocationAlreadyExistsException.class)
    private ResponseEntity<RelocationErrorResponse> handleRelocationAlreadyExistsException(RelocationAlreadyExistsException exception) {
        return new ResponseEntity<>(new RelocationErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InformationBlockAlreadyExistsException.class)
    private ResponseEntity<InformationBlockErrorResponse> handleRelocationAlreadyExistsException(InformationBlockAlreadyExistsException exception) {
        return new ResponseEntity<>(new InformationBlockErrorResponse(exception.getMessage()), HttpStatus.CONFLICT);
    }
}






























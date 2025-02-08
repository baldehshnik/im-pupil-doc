package im.pupil.api.presentation.auth;

import im.pupil.api.domain.exception.security.sign.up.admin.AdminAlreadyRegisteredException;
import im.pupil.api.domain.exception.security.sign.up.admin.response.AdminAuthErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerExceptionHandler {

    @ExceptionHandler({AdminAlreadyRegisteredException.class})
    public ResponseEntity<AdminAuthErrorResponse> handleRoleNotFoundException(Exception exception) {
        if (exception instanceof AdminAlreadyRegisteredException adminAlreadyRegisteredException) {
            HttpStatus httpStatus = HttpStatus.CONFLICT;
            return handleAdminAlreadyRegisteredException(adminAlreadyRegisteredException, httpStatus);
        } else {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleInternalError(exception, httpStatus);
        }
    }

    protected ResponseEntity<AdminAuthErrorResponse> handleAdminAlreadyRegisteredException(
            AdminAlreadyRegisteredException exception,
            HttpStatus httpStatus) {
        return new ResponseEntity<>(new AdminAuthErrorResponse(exception.getMessage()), httpStatus);
    }

    protected ResponseEntity<AdminAuthErrorResponse> handleInternalError(Exception exception, HttpStatus httpStatus) {
        return new ResponseEntity<>(new AdminAuthErrorResponse(exception.getMessage()), httpStatus);
    }
}

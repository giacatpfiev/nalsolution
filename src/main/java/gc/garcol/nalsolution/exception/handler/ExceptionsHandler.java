package gc.garcol.nalsolution.exception.handler;

import gc.garcol.nalsolution.exception.FirebaseRuntimeException;
import gc.garcol.nalsolution.payload.res.error.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author thai-van
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseError handleException(Exception e, HttpServletRequest request) {
        return buildErrorMessage(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class, AuthenticationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseError handleSpecificException(Exception e, HttpServletRequest request) {
        return buildErrorMessage(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FirebaseRuntimeException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseError handleFirebaseException(Exception e, HttpServletRequest request) {
        return buildErrorMessage(e, request, HttpStatus.UNAUTHORIZED);
    }

    private ResponseError buildErrorMessage(Exception e, HttpServletRequest request, HttpStatus status) {
        ResponseError error = ResponseError.builder()
                .localDateTime(LocalDateTime.now())
                .message(e.getMessage())
                .path("[" + request.getMethod() + "]" +request.getRequestURI())
                .status(status.value())
                .build();

        log.error("{}", error.toString(), e);

        return error;
    }
}

package com.baseApp.backend.exceptions;

import com.baseApp.backend.exceptions.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse =  new ErrorResponse(
                tl("bad_credentials"),
                ex.getClass().getSimpleName(),
                HttpStatus.UNAUTHORIZED.value(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(RefreshTokenException ex) {
        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.FORBIDDEN.value(),
                ZonedDateTime.now());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.FORBIDDEN
        );
    }

}

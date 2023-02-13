package com.baseApp.backend.exceptions;

import com.baseApp.backend.exceptions.responses.ErrorResponse;
import com.baseApp.backend.exceptions.responses.ErrorValidatorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.baseApp.backend.utils.TranslateUtils;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

import static com.baseApp.backend.utils.TranslateUtils.tl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).map(TranslateUtils::tl).collect(Collectors.toList());

        ErrorValidatorResponse errorResponse =  new ErrorValidatorResponse(
                errors,
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                request.getServletPath()
        );
        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse =  new ErrorResponse(
                tl("bad_credentials"),
                ex.getClass().getSimpleName(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(RefreshTokenException ex, HttpServletRequest request) {
        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.FORBIDDEN.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleUserException(UserException ex, HttpServletRequest request) {

        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = RoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleRoleException(RoleException ex, HttpServletRequest request) {
        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handlePermissionException(PermissionException ex, HttpServletRequest request) {

        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleNotificationException(NotificationException ex, HttpServletRequest request) {

        ErrorResponse errorResponse =  new ErrorResponse(
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                request.getServletPath()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

}

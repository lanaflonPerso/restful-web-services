package org.munozy.rest.webservices.restfulwebservices.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ExceptionReponse exceptionReponse = ExceptionReponse
                .of(LocalDate.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionReponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        int length = exception.getBindingResult().getAllErrors().size();

        ExceptionReponse exceptionReponse = ExceptionReponse
                .of(LocalDate.now(),
                        exception.getBindingResult().getAllErrors().get(length -1).getDefaultMessage(),
                        webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValidException(InvalidFormatException exception, WebRequest webRequest) {
        String message;
        if (exception.getCause() instanceof DateTimeParseException) {
            message = "The date pattern is \"yyyy-MM-dd\"";
        } else {
            message = exception.getCause().toString();
        }
        ExceptionReponse exceptionReponse = ExceptionReponse
                .of(LocalDate.now(), message, webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RollbackException.class)
    public final ResponseEntity<Object> handleRollbackException(RollbackException exception, WebRequest webRequest) {
        String message;
        if (exception.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause();
            String[] strings = constraintViolationException.getConstraintViolations().iterator().next().toString().split(",");
            String interpolatedMessage = strings[0].substring(strings[0].indexOf("'") + 1, strings[0].lastIndexOf("'"));
            String propertyPath = strings[1].substring(strings[1].lastIndexOf("=") + 1);
            message = "[" + propertyPath + "] - " + interpolatedMessage;
        } else {
            message = exception.getCause().toString();
        }
        ExceptionReponse exceptionReponse = ExceptionReponse
                .of(LocalDate.now(), message, webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionReponse, HttpStatus.BAD_REQUEST);
    }
}

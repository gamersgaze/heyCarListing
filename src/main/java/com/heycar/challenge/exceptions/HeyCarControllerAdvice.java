package com.heycar.challenge.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/*
 * used this to format the default spring error responses
 * it looks bad if entire stackTrace is printed in response (which is spring default behavior)
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HeyCarControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeyCarControllerAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RestApiErrorTemplate> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        RestApiErrorTemplate badRequestError = new RestApiErrorTemplate(status);
        badRequestError.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(badRequestError, status);
    }

    @ExceptionHandler(FileReadingException.class)
    public ResponseEntity<RestApiErrorTemplate> handleFileReadingException(FileReadingException e, HttpServletRequest request) {
        LOGGER.error("FileReadingExceptions : {}", e);
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        RestApiErrorTemplate FileReadingError = new RestApiErrorTemplate(status);
        FileReadingError.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(FileReadingError, status);
    }

    @ExceptionHandler(NoValidListingFoundException.class)
    public ResponseEntity<RestApiErrorTemplate> handleNoValidListingException(NoValidListingFoundException e, HttpServletRequest request) {
        final HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        RestApiErrorTemplate noValidListingError = new RestApiErrorTemplate(status);
        noValidListingError.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(noValidListingError, status);
    }

}

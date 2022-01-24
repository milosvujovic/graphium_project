package uk.ac.cardiff.team5.graphium.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.cardiff.team5.graphium.exception.OrganisationNotFoundException;

@ControllerAdvice
public class OrganisationNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(OrganisationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String organisationNotFoundHandler(OrganisationNotFoundException exception) {
        return exception.getMessage();
    }
}
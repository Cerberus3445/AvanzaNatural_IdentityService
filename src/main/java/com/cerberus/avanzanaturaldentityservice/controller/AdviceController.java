package com.cerberus.avanzanaturaldentityservice.controller;

import com.cerberus.avanzanaturaldentityservice.exception.AuthorizationException;
import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleException(ValidationException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, exception.getMessage()
        );
        problemDetail.setTitle("Validation Exception");
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleException(NotFoundException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, exception.getMessage()
        );
        problemDetail.setTitle("Not found");
        return problemDetail;
    }

    @ExceptionHandler(AuthorizationException.class)
    public ProblemDetail handleException(AuthorizationException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, exception.getMessage()
        );
        problemDetail.setTitle("Authorization exception");
        return problemDetail;
    }
}

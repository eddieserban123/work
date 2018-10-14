package com.example.rest.webservices.restfulwebservices.exception;

import com.example.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

        ExceptionResponse exp =
        new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));


        return new ResponseEntity(exp, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {

        ExceptionResponse exp =
                new ExceptionResponse(new Date(), ex.getMessage(),
                        request.getDescription(false));


        return new ResponseEntity(exp, HttpStatus.NOT_FOUND);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exp =
                new ExceptionResponse(new Date(), ex.getMessage(),
                        ex.getBindingResult().toString());
        return new ResponseEntity(exp, HttpStatus.BAD_REQUEST);

    }
}
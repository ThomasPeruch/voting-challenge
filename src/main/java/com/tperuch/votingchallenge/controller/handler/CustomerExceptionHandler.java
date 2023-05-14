package com.tperuch.votingchallenge.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new ErrorMessage(
                exception.getClass().getName(),
                HttpStatus.BAD_REQUEST.value(),
                List.of("O(s) campo(s) inserido(s) no request não deve(m) ser vazio(s)")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getClass().getName(), HttpStatus.BAD_REQUEST.value(), List.of(exception.getLocalizedMessage())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getClass().getName(), HttpStatus.NOT_FOUND.value(), List.of(exception.getMessage())),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> runtimeException(RuntimeException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(exception.getClass().getName(), HttpStatus.NOT_FOUND.value(), List.of(exception.getMessage())),
                HttpStatus.NOT_FOUND);
    }

}

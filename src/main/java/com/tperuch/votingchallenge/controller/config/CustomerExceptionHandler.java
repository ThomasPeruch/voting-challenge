package com.tperuch.votingchallenge.controller.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                List.of("O(s) campo(s) inserido(s) no request não deve(m) ser vazio(s)")),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(
                new ErrorMessage(HttpStatus.BAD_REQUEST.value(),List.of("O Json de request não pode ser nulo/vazio")),
                HttpStatus.BAD_REQUEST);
    }
}

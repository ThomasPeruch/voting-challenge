package com.tperuch.votingchallenge.controller.config;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorMessage {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime time;
    private Class exceptionClass;
    private int httpStatus;
    private List<String> errors;

    public ErrorMessage(Class exceptionClass, int httpStatus, List<String> errors) {
        this.exceptionClass = exceptionClass;
        this.time = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public ErrorMessage() {
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Class getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(Class exceptionClass) {
        this.exceptionClass = exceptionClass;
    }
}

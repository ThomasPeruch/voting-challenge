package com.tperuch.votingchallenge.controller.config;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorMessage {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime time;
    private int httpStatus;
    private List<String> errors;

    public ErrorMessage(int httpStatus, List<String> errors) {
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
}

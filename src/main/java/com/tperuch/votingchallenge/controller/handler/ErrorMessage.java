package com.tperuch.votingchallenge.controller.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorMessage {


    @ApiModelProperty(value = "Data que ocorreu o erro")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime time;

    @ApiModelProperty(value = "Exceção que gerou o erro")
    private String exceptionClass;

    @ApiModelProperty(value = "Status HTTP retornado")
    private int httpStatus;

    @ApiModelProperty(value = "Mensagens de erro")
    private List<String> errors;

    public ErrorMessage(String exceptionClass, int httpStatus, List<String> errors) {
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

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }
}

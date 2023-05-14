package com.tperuch.votingchallenge.controller.topic.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class TopicRequest {

    @ApiModelProperty(value = "Descrição/proposta da pauta")
    @NotBlank(message = "A descrição da pauta é obrigatória")
    private String description;

    public TopicRequest() {
    }

    public TopicRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicRequest that = (TopicRequest) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}

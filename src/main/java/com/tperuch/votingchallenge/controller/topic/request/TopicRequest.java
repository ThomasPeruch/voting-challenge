package com.tperuch.votingchallenge.controller.topic.request;

import javax.validation.constraints.NotBlank;

public class TopicRequest {

    @NotBlank(message = "A descrição da pauta é obrigatória")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

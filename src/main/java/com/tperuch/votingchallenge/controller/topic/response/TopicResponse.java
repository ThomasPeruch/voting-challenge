package com.tperuch.votingchallenge.controller.topic.response;

import io.swagger.annotations.ApiModelProperty;

public class TopicResponse {

    @ApiModelProperty(value = "Id da pauta")
    private Long id;

    @ApiModelProperty(value = "Descrição/proposta da pauta")
    private String description;

    public TopicResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TopicResponse(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}

package com.tperuch.votingchallenge.controller.topic.response;

public class TopicResponse {

    private Long id;
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

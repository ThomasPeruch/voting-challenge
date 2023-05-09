package com.tperuch.votingchallenge.controller.session.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SessionResponse {

    private Long id;

    private Long topicId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime votingStart;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime votingEnd;

    public SessionResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public LocalDateTime getVotingStart() {
        return votingStart;
    }

    public void setVotingStart(LocalDateTime votingStart) {
        this.votingStart = votingStart;
    }

    public LocalDateTime getVotingEnd() {
        return votingEnd;
    }

    public void setVotingEnd(LocalDateTime votingEnd) {
        this.votingEnd = votingEnd;
    }
}

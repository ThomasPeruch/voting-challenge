package com.tperuch.votingchallenge.controller.session.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionResponse that = (SessionResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(topicId, that.topicId) && Objects.equals(votingStart, that.votingStart) && Objects.equals(votingEnd, that.votingEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicId, votingStart, votingEnd);
    }
}

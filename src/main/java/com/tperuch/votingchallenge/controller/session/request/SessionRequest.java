package com.tperuch.votingchallenge.controller.session.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionRequest {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime votingEnd;

    public SessionRequest() {
    }

    public SessionRequest(LocalDateTime votingEnd) {
        this.votingEnd = votingEnd;
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
        SessionRequest that = (SessionRequest) o;
        return Objects.equals(votingEnd, that.votingEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(votingEnd);
    }
}

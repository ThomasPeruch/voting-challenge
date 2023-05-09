package com.tperuch.votingchallenge.controller.session.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SessionRequest {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime votingEnd;

    public SessionRequest() {
    }

    public LocalDateTime getVotingEnd() {
        return votingEnd;
    }

    public void setVotingEnd(LocalDateTime votingEnd) {
        this.votingEnd = votingEnd;
    }
}

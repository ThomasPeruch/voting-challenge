package com.tperuch.votingchallenge.controller.session.response;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class VoteResponse {

    private Long id;

    @NotNull
    private Long associateId;

    @NotNull
    private VoteEnum voteValue;

    @NotNull
    private Long sessionId;


    public VoteResponse() {
    }

    public VoteResponse(Long id, Long sessionId, Long associateId, VoteEnum voteValue) {
        this.id = id;
        this.sessionId = sessionId;
        this.associateId = associateId;
        this.voteValue = voteValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssociateId() {
        return associateId;
    }

    public void setAssociateId(Long associateId) {
        this.associateId = associateId;
    }

    public VoteEnum getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(VoteEnum voteValue) {
        this.voteValue = voteValue;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteResponse that = (VoteResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(associateId, that.associateId) && voteValue == that.voteValue && Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, associateId, voteValue, sessionId);
    }
}

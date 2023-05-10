package com.tperuch.votingchallenge.controller.session.request;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;

import javax.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull
    private Long sessionVotingId;

    @NotNull
    private Long associateId;

    @NotNull
    private VoteEnum voteValue;

    public VoteRequest() {
    }

    public Long getSessionVotingId() {
        return sessionVotingId;
    }

    public void setSessionVotingId(Long sessionVotingId) {
        this.sessionVotingId = sessionVotingId;
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
}

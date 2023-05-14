package com.tperuch.votingchallenge.controller.session.request;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class VoteRequest {

    @ApiModelProperty(value = "Id da sessão de votação")
    @NotNull
    private Long sessionVotingId;

    @ApiModelProperty(value = "Id do associado que ira votar")
    @NotNull
    private Long associateId;

    @ApiModelProperty(value = "Voto do associado")
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

    public void setVoteValue(VoteEnum voteValue) {
        this.voteValue = voteValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteRequest that = (VoteRequest) o;
        return Objects.equals(sessionVotingId, that.sessionVotingId) && Objects.equals(associateId, that.associateId) && voteValue == that.voteValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionVotingId, associateId, voteValue);
    }
}

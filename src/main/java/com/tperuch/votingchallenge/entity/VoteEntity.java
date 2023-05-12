package com.tperuch.votingchallenge.entity;

import javax.persistence.*;

@Entity
@Table(name = "vote")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long associateId;
    private Boolean voteValue;
    private Long sessionId;

    public VoteEntity() {
    }

    public VoteEntity(Long associateId, boolean voteValue, Long sessionId) {
        this.associateId = associateId;
        this.voteValue = voteValue;
        this.sessionId = sessionId;
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

    public boolean isVoteValue() {
        return voteValue;
    }

    public void setVoteValue(boolean voteValue) {
        this.voteValue = voteValue;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(Boolean voteValue) {
        this.voteValue = voteValue;
    }
}

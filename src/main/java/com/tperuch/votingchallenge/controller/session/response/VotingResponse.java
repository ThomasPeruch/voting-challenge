package com.tperuch.votingchallenge.controller.session.response;

import com.tperuch.votingchallenge.controller.session.enums.VotingResultEnum;

public class VotingResponse {

    private Long topicId;
    private Long sessionId;
    private Integer votesForYes;
    private Integer votesForNo;
    private VotingResultEnum result;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getVotesForYes() {
        return votesForYes;
    }

    public void setVotesForYes(Integer votesForYes) {
        this.votesForYes = votesForYes;
    }

    public Integer getVotesForNo() {
        return votesForNo;
    }

    public void setVotesForNo(Integer votesForNo) {
        this.votesForNo = votesForNo;
    }

    public VotingResultEnum getResult() {
        return result;
    }

    public void setResult(VotingResultEnum result) {
        this.result = result;
    }
}

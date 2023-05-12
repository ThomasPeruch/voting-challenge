package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;

public class VoteRequestStub {
    public static VoteRequest get(){
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setSessionVotingId(1L);
        voteRequest.setAssociateId(1L);
        voteRequest.setVoteValue(VoteEnum.SIM);
        return voteRequest;
    }
}

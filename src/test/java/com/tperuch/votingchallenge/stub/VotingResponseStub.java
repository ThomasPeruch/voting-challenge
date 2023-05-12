package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.session.enums.VotingResultEnum;
import com.tperuch.votingchallenge.controller.session.response.VotingResponse;

public class VotingResponseStub {
    public static VotingResponse getApprovedVoting(){
        VotingResponse votingResponse = new VotingResponse();
        votingResponse.setSessionId(1L);
        votingResponse.setTopicId(1L);
        votingResponse.setVotesForNo(0);
        votingResponse.setVotesForYes(1);
        votingResponse.setResult(VotingResultEnum.APROVADA);
        return votingResponse;
    }
}

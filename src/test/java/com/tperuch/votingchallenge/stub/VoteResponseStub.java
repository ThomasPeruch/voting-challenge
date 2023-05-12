package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import com.tperuch.votingchallenge.controller.session.response.VoteResponse;

public class VoteResponseStub {
    public static VoteResponse get(){
        return new VoteResponse(1L, 1L, 1L, VoteEnum.SIM);
    }
}

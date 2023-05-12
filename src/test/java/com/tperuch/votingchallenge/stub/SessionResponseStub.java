package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.utils.DateParser;

public class SessionResponseStub {
    public static SessionResponse get(){
        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setId(1L);
        sessionResponse.setTopicId(1L);
        sessionResponse.setVotingStart(DateParser.parseDateFromString("05-10-2023 10:10"));
        sessionResponse.setVotingEnd(DateParser.parseDateFromString("05-10-2023 10:10"));
        return sessionResponse;
    }
}

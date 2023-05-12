package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.utils.DateParser;

public class SessionEntityStub {

    public static SessionEntity get(){
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(1L);
        sessionEntity.setTopicId(1L);
        sessionEntity.setVotesForYes(0);
        sessionEntity.setVotesForNo(0);
        sessionEntity.setVotingStart(DateParser.parseDateFromString("01-05-2023 10:10"));
        sessionEntity.setVotingEnd(DateParser.parseDateFromString("02-05-2023 10:10"));
        return sessionEntity;
    }
}

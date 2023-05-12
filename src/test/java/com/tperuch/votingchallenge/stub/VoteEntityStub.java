package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.entity.VoteEntity;

public class VoteEntityStub {
    public static VoteEntity get(){
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setSessionId(1L);
        voteEntity.setAssociateId(1L);
        voteEntity.setVoteValue(Boolean.TRUE);
        voteEntity.setId(1L);
        return voteEntity;
    }
}

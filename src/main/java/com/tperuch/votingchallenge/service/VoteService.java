package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.entity.VoteEntity;
import com.tperuch.votingchallenge.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public boolean associateAlreadyVoted(Long associateId, Long sessionId){
        return voteRepository.existsByAssociateIdAndSessionId(associateId, sessionId);
    }

    public VoteEntity saveVote(Long sessionId, VoteRequest voteRequest) {
        return voteRepository.save(buildVoteEntity(sessionId, voteRequest));
    }

    private VoteEntity buildVoteEntity(Long sessionId, VoteRequest voteRequest) {
        return new VoteEntity(voteRequest.getAssociateId(), voteRequest.getVoteValue().getVoteValue(), sessionId);
    }
}

package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.controller.session.response.VoteResponse;
import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.entity.VoteEntity;
import com.tperuch.votingchallenge.service.SessionService;
import com.tperuch.votingchallenge.service.TopicService;
import com.tperuch.votingchallenge.service.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class SessionFacade {

    @Autowired
    private TopicService topicService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private ModelMapper modelMapper;

    public SessionResponse openVotingSession(Long topicId, SessionRequest sessionRequest) {
        if (!topicService.existsById(topicId)) {
            throw new EntityNotFoundException("Não foi encontrado nenhuma pauta com o id informado - ID: " + topicId);
        }
        return modelMapper.map(sessionService.saveVotingSession(topicId, sessionRequest.getVotingEnd()), SessionResponse.class);
    }

    public VoteResponse vote(VoteRequest voteRequest){
        SessionEntity sessionEntity = sessionService.findVotingSessionById(voteRequest.getSessionVotingId());
        if(sessionService.isSessionClosedForVoting(sessionEntity)){
            throw new RuntimeException("A sessão escolhida ja teve sua votação encerrada");
        }
        if(associateHasNotVotedYet(voteRequest.getAssociateId(), sessionEntity.getId())) {
            sessionService.updateVoting(sessionEntity, voteRequest);
            VoteEntity voteEntity = voteService.saveVote(sessionEntity.getId(), voteRequest);
            return mapToResponse(voteEntity);
        }
        throw new RuntimeException("O Associado de ID " + voteRequest.getAssociateId()
                    + " já votou na sessão de ID " + sessionEntity.getId()
                    + ", só é permitido um voto por associado para cada sessão");

    }

    private VoteResponse mapToResponse(VoteEntity voteEntity) {
        return new VoteResponse(voteEntity.getId(),
                voteEntity.getSessionId(),
                voteEntity.getAssociateId(),
                VoteEnum.getVoteEnumByValue(voteEntity.isVoteValue()));
    }

    private boolean associateHasNotVotedYet(Long associateId, Long votingSessionId){
        return !voteService.associateAlreadyVoted(associateId, votingSessionId);
    }
}

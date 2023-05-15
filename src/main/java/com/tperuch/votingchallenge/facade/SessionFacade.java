package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.session.enums.SessionStatusEnum;
import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import com.tperuch.votingchallenge.controller.session.enums.VotingResultEnum;
import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.controller.session.response.SessionStatusResponse;
import com.tperuch.votingchallenge.controller.session.response.VoteResponse;
import com.tperuch.votingchallenge.controller.session.response.VotingResponse;
import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.entity.VoteEntity;
import com.tperuch.votingchallenge.exception.AssociateAlreadyVotedException;
import com.tperuch.votingchallenge.exception.SessionClosedException;
import com.tperuch.votingchallenge.service.SessionService;
import com.tperuch.votingchallenge.service.TopicService;
import com.tperuch.votingchallenge.service.VoteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    Logger logger = LoggerFactory.getLogger(SessionFacade.class);

    public SessionResponse openVotingSession(Long topicId, SessionRequest sessionRequest) {
        if (!topicService.existsById(topicId)) {
            logger.error("Não foi encontrado nenhuma pauta com o id informado - ID: {}",topicId);
            throw new EntityNotFoundException("Não foi encontrado nenhuma pauta com o id informado - ID: " + topicId);
        }
        return modelMapper.map(sessionService.createVotingSession(topicId, sessionRequest.getVotingEnd()), SessionResponse.class);
    }

    public VoteResponse vote(VoteRequest voteRequest){
        logger.info("Registrando voto na sessão de ID {}", voteRequest.getSessionVotingId());
        SessionEntity sessionEntity = sessionService.findVotingSessionById(voteRequest.getSessionVotingId());
        if(sessionService.isSessionClosedForVoting(sessionEntity)){
            logger.error("A sessão escolhida ja teve sua votação encerrada");
            throw new SessionClosedException("A sessão escolhida ja teve sua votação encerrada");
        }
        if(associateHasNotVotedYet(voteRequest.getAssociateId(), sessionEntity.getId())) {
            logger.info("O associado de ID {} está apto a votar nessa sessão", voteRequest.getAssociateId());
            sessionService.updateVoting(sessionEntity, voteRequest);
            VoteEntity voteEntity = voteService.saveVote(sessionEntity.getId(), voteRequest);
            return mapToResponse(voteEntity);
        }
        logger.error("O Associado de ID {} já votou na sessão de ID {}, só é permitido um voto por associado para cada sessão",
                voteRequest.getAssociateId(), sessionEntity.getId());
        throw new AssociateAlreadyVotedException(
                    "O Associado de ID " + voteRequest.getAssociateId()
                    + " já votou na sessão de ID " + sessionEntity.getId()
                    + ", só é permitido um voto por associado para cada sessão");

    }

    public VotingResponse countVotesAndGetSessionVotingResult(Long sessionId){
        logger.info("Buscando votos e resultado da sessão de ID {}", sessionId);
        SessionEntity sessionEntity = sessionService.findVotingSessionById(sessionId);
        return buildVotingResponse(sessionEntity);
    }

    public List<SessionStatusResponse> findAllVotingSessions(){
        List<SessionEntity> sessionEntity = sessionService.findAllVotingSessions();
        if(Objects.isNull(sessionEntity) || sessionEntity.isEmpty()){
            logger.error("Não existe nenhuma sessão de votação cadastrada");
            throw new EntityNotFoundException("Não existe nenhuma sessão de votação cadastrada");
        }
        return buildSessionStatusResponse(sessionEntity);
    }

    private List<SessionStatusResponse> buildSessionStatusResponse(List<SessionEntity> sessions) {
        return sessions.stream().map(this::mapToSessionStatusResponse).collect(Collectors.toList());
    }

    private SessionStatusResponse mapToSessionStatusResponse(SessionEntity sessionEntity) {
        SessionStatusResponse sessionStatusResponse = new SessionStatusResponse();
        sessionStatusResponse.setId(sessionEntity.getId());
        sessionStatusResponse.setTopicId(sessionEntity.getTopicId());
        sessionStatusResponse.setStatus(getSessionStatus(sessionEntity.getVotingEnd()));
        return sessionStatusResponse;
    }

    private SessionStatusEnum getSessionStatus(LocalDateTime votingEnd) {
        if(Objects.isNull(votingEnd)) return SessionStatusEnum.NAO_INICIADA;
        if(votingEnd.isAfter(LocalDateTime.now())) return SessionStatusEnum.ABERTA;
        return SessionStatusEnum.ENCERRADA;
    }


    private VotingResponse buildVotingResponse(SessionEntity sessionEntity) {
        Integer votesForNo = getVotesQuantity(sessionEntity.getVotesForNo());
        Integer votesForYes = getVotesQuantity(sessionEntity.getVotesForYes());
        VotingResultEnum votingResultEnum = getVotingResult(sessionEntity.getVotingEnd(), votesForNo, votesForYes);

        VotingResponse votingResponse = new VotingResponse();
        votingResponse.setSessionId(sessionEntity.getId());
        votingResponse.setTopicId(sessionEntity.getTopicId());
        votingResponse.setVotesForNo(votesForNo);
        votingResponse.setVotesForYes(votesForYes);
        votingResponse.setResult(votingResultEnum);
        return votingResponse;
    }

    private VotingResultEnum getResult(Integer votesForYes, Integer votesForNo) {
        int comparison = Integer.compare(votesForYes, votesForNo);
        switch (comparison){
            case 0 :
                return VotingResultEnum.EMPATE;
            case 1 :
                return VotingResultEnum.APROVADA;
            default:
                return VotingResultEnum.REJEITADA;
        }
    }

    private VotingResultEnum getVotingResult(LocalDateTime votingEnd, Integer noVotes, Integer yesVotes) {
        if(isVotingSessionClosed(votingEnd)){
            return getResult(yesVotes, noVotes);
        } else {
            return VotingResultEnum.EM_ABERTO;
        }
    }

    private boolean isVotingSessionClosed(LocalDateTime votingEnd) {
        return votingEnd.isBefore(LocalDateTime.now());
    }

    private Integer getVotesQuantity(Integer votesQuantity) {
        return Objects.nonNull(votesQuantity) ? votesQuantity : 0 ;
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

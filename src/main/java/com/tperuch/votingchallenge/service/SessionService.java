package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.exception.InvalidDateException;
import com.tperuch.votingchallenge.exception.TopicAlreadyInVotingException;
import com.tperuch.votingchallenge.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SessionService {

    Logger logger = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private SessionRepository sessionRepository;

    public SessionEntity createVotingSession(Long topicId, LocalDateTime votingEndDate) {
        logger.info("Abrindo uma sessão de votação");
        checkIfTopicIsAlreadyInVoting(topicId);
        LocalDateTime votingEnd = getVotingEndDate(votingEndDate);
        return sessionRepository.save(buildOpeningVotingSession(topicId, votingEnd));
    }

    public SessionEntity findVotingSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não foi encontrado nenhuma sessão de votaçao para o id informado - ID: " + id));
    }

    public List<SessionEntity> findAllVotingSessions() {
        logger.info("Buscando todas as sessões de votação");
        return sessionRepository.findAll();
    }

    private void checkIfTopicIsAlreadyInVoting(Long topicId) {
        logger.info("Verificando disponibilidade da pauta para votação - ID da pauta: {}", topicId);
        SessionEntity sessionEntity = sessionRepository.findByTopicId(topicId);
        if (Objects.isNull(sessionEntity)) {
            logger.info("A pauta esta disponivel para votação - ID da pauta: {}", topicId);
        } else if (Objects.nonNull(sessionEntity.getVotingStart()) && Objects.nonNull(sessionEntity.getVotingEnd())) {
            logger.error("Outra sessão de votação já foi iniciada com essa pauta de ID {}, tente iniciar uma sessão com outra pauta", topicId);
            throw new TopicAlreadyInVotingException("Outra sessão de votação já foi iniciada com essa pauta de ID " + topicId + ", tente iniciar uma sessão com outra pauta");
        }
    }

    private LocalDateTime getVotingEndDate(LocalDateTime votingEndRequest) {
        LocalDateTime endingVote;
        if (Objects.isNull(votingEndRequest)) {
            endingVote = LocalDateTime.now().plusMinutes(1);
        } else {
            endingVote = votingEndRequest;
        }
        return checkVotingEndDate(endingVote);
    }

    private SessionEntity buildOpeningVotingSession(Long topicId, LocalDateTime votingEndDate) {
        return new SessionEntity(topicId, LocalDateTime.now(), votingEndDate);
    }

    private LocalDateTime checkVotingEndDate(LocalDateTime votingEnd) {
    logger.info("Verificando se a data e hora de encerramento da votação são válidos");
        if (votingEnd.isBefore(LocalDateTime.now())) {
            logger.error("A data final deve ser posterior à data inicial da votação");
            throw new InvalidDateException("A data final deve ser posterior à data inicial da votação");
        }
        return votingEnd;
    }

    public void updateVoting(SessionEntity sessionEntity, VoteRequest voteRequest) {
        logger.info("Atualizando registro de votos, valor do voto: {}", voteRequest.getVoteValue());
        VoteEnum enumVote = isVoteValid(voteRequest.getVoteValue());
        SessionEntity sessionToUpdate = sessionEntity;
        if (Boolean.TRUE.equals(enumVote.getVoteValue())) {
            sessionToUpdate.setVotesForYes(Integer.sum(getVotesQuantity(sessionEntity.getVotesForYes()), 1));
        } else {
            sessionToUpdate.setVotesForNo(Integer.sum(getVotesQuantity(sessionEntity.getVotesForNo()), 1));
        }
        sessionRepository.save(sessionToUpdate);
    }

    private VoteEnum isVoteValid(VoteEnum voteValue) {
        return VoteEnum.valueOf(voteValue.name());
    }

    private Integer getVotesQuantity(Integer voteQuantity) {
        return Objects.isNull(voteQuantity) ? 0 : voteQuantity;
    }

    public boolean isSessionClosedForVoting(SessionEntity sessionEntity) {
        return sessionEntity.getVotingEnd().isBefore(LocalDateTime.now());
    }
}

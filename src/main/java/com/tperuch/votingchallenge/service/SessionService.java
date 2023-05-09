package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SessionService {

    Logger logger = LoggerFactory.getLogger(SessionService.class);
    @Autowired
    private SessionRepository sessionRepository;

    public SessionEntity saveVotingSession(Long topicId, LocalDateTime votingEndDate) {
        checkIfTopicIsAlreadyInVoting(topicId);
        LocalDateTime votingEnd = getVotingEndDate(votingEndDate);
        return sessionRepository.save(buildOpeningVotingSession(topicId, votingEnd));
    }

    private void checkIfTopicIsAlreadyInVoting(Long topicId) {
        SessionEntity sessionEntity = sessionRepository.findByTopicId(topicId);
        if (Objects.isNull(sessionEntity)) {
            logger.info("A pauta esta disponivel para votação - ID da pauta: {}", topicId);
        } else if (Objects.nonNull(sessionEntity.getVotingStart()) && Objects.nonNull(sessionEntity.getVotingEnd())) {
            throw new RuntimeException("Outra sessão de votação já foi iniciada com essa pauta de ID " + topicId + ", tente iniciar uma sessão com outra pauta");
        }
    }

    private SessionEntity buildOpeningVotingSession(Long topicId, LocalDateTime votingEndDate) {
        return new SessionEntity(topicId, LocalDateTime.now(), votingEndDate);
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

    private LocalDateTime checkVotingEndDate(LocalDateTime votingEnd) {
        if (votingEnd.isBefore(LocalDateTime.now())) {
            throw new DateTimeException("A data final deve ser posterior à data inicial da votação");
        }
        return votingEnd;
    }
}

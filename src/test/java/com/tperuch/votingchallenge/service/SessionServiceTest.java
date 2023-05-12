package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.controller.session.enums.VoteEnum;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.exception.InvalidDateException;
import com.tperuch.votingchallenge.repository.SessionRepository;
import com.tperuch.votingchallenge.stub.SessionEntityStub;
import com.tperuch.votingchallenge.stub.VoteRequestStub;
import com.tperuch.votingchallenge.utils.DateParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void shouldFindVotingSessionById() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(SessionEntityStub.get()));

        sessionService.findVotingSessionById(1L);

        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenNotFindVotingSessionById() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows( EntityNotFoundException.class, () -> sessionService.findVotingSessionById(1L));

        verify(sessionRepository, times(1)).findById(1L);
        assertEquals("Não foi encontrado nenhuma sessão de votaçao para o id informado - ID: " + 1L, exception.getMessage());
    }

    @Test
    void shouldSaveVotingSessionWithVotingEndDateDefault() {
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotingEnd(null);
        sessionEntity.setVotingStart(null);

        when(sessionRepository.findByTopicId(1L)).thenReturn(null);
        when(sessionRepository.save(any())).thenReturn(SessionEntityStub.get());

        sessionService.createVotingSession(1L, null);

        verify(sessionRepository, times(1)).findByTopicId(1L);
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    void shouldSaveVotingSessionWithCUstomVotingEndDate() {
        LocalDateTime endVoteDate = DateParser.parseDateFromString("21-12-2023 15:00");
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotingEnd(endVoteDate);
        sessionEntity.setVotingStart(null);

        when(sessionRepository.findByTopicId(1L)).thenReturn(null);

        sessionService.createVotingSession(1L, endVoteDate);

        verify(sessionRepository, times(1)).findByTopicId(1L);
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    void shouldNotSaveVotingSessionBecauseVotingEndDateIsBeforeStartDate() {
        LocalDateTime endVoteDate = DateParser.parseDateFromString("01-01-2023 11:09");
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotingEnd(endVoteDate);
        sessionEntity.setVotingStart(null);

        when(sessionRepository.findByTopicId(1L)).thenReturn(null);

        InvalidDateException exception = assertThrows(
                InvalidDateException.class, () -> sessionService.createVotingSession(1L, endVoteDate));

        verify(sessionRepository, times(1)).findByTopicId(1L);
        verify(sessionRepository, never()).save(any());
        assertEquals("A data final deve ser posterior à data inicial da votação", exception.getMessage());
    }

    @Test
    void shouldNotCreateVotingSessionBecauseThereIsAVotingSessionCreatedWithTopicId() {
        LocalDateTime endVoteDate = DateParser.parseDateFromString("01-01-2023 11:09");
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotingEnd(endVoteDate);

        when(sessionRepository.findByTopicId(1L)).thenReturn(sessionEntity);

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> sessionService.createVotingSession(1L, endVoteDate));

        verify(sessionRepository, times(1)).findByTopicId(1L);
        verify(sessionRepository, never()).save(any());
        assertEquals("Outra sessão de votação já foi iniciada com essa pauta de ID " + 1L + ", tente iniciar uma sessão com outra pauta",
                exception.getMessage());
    }

    @Test
    void isSessionClosedForVoting(){
        assertTrue(sessionService.isSessionClosedForVoting(SessionEntityStub.get()));
    }

    @Test
    void isSessionOpenForVoting(){
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotingEnd(DateParser.parseDateFromString("21-12-2023 18:40"));
        assertFalse(sessionService.isSessionClosedForVoting(sessionEntity));
    }

    @Test
    void shouldUpdateVotingYes(){
        VoteRequest voteRequest = VoteRequestStub.get();
        SessionEntity sessionEntity = SessionEntityStub.get();

        sessionService.updateVoting(sessionEntity, voteRequest);

        verify(sessionRepository,times(1)).save(any());
    }

    @Test
    void shouldUpdateVotingNo(){
        VoteRequest voteRequest = VoteRequestStub.get();
        voteRequest.setVoteValue(VoteEnum.NAO);
        SessionEntity sessionEntity = SessionEntityStub.get();

        sessionService.updateVoting(sessionEntity, voteRequest);

        verify(sessionRepository,times(1)).save(any());
    }
}
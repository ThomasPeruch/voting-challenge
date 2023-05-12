package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.session.enums.VotingResultEnum;
import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.controller.session.response.VotingResponse;
import com.tperuch.votingchallenge.entity.SessionEntity;
import com.tperuch.votingchallenge.entity.VoteEntity;
import com.tperuch.votingchallenge.service.SessionService;
import com.tperuch.votingchallenge.service.TopicService;
import com.tperuch.votingchallenge.service.VoteService;
import com.tperuch.votingchallenge.stub.*;
import com.tperuch.votingchallenge.utils.DateParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionFacadeTest {

    @Mock
    private TopicService topicService;
    @Mock
    private SessionService sessionService;
    @Mock
    private VoteService voteService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private SessionFacade sessionFacade;

    @Test
    void shouldOpenVotingSession() {
        SessionEntity sessionStub = SessionEntityStub.get();
        SessionRequest sessionRequest = SessionRequestStub.get();
        SessionResponse sessionResponse = SessionResponseStub.get();

        when(topicService.existsById(anyLong())).thenReturn(true);
        when(sessionService.createVotingSession(anyLong(), eq(sessionRequest.getVotingEnd())))
                .thenReturn(sessionStub);
        when(modelMapper.map(sessionStub, SessionResponse.class)).thenReturn(sessionResponse);

        SessionResponse result = sessionFacade.openVotingSession(1L, sessionRequest);

        assertEquals(result.getId(), sessionResponse.getId());
        assertEquals(result.getTopicId(), sessionResponse.getTopicId());
        assertEquals(result.getVotingEnd(), sessionResponse.getVotingEnd());
        assertEquals(result.getVotingStart(), sessionResponse.getVotingStart());
    }

    @Test
    void shouldNotOpenVotingSessionBecauseTopicIdDoesNotExist() {
        when(topicService.existsById(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sessionFacade.openVotingSession(1L, any()));

        assertEquals(exception.getMessage(), "Não foi encontrado nenhuma pauta com o id informado - ID: " + 1L);
    }

    @Test
    void shouldVote() {
        VoteRequest voteRequest = VoteRequestStub.get();
        SessionEntity sessionEntity = SessionEntityStub.get();
        VoteEntity vote = VoteEntityStub.get();

        when(sessionService.findVotingSessionById(voteRequest.getSessionVotingId())).thenReturn(sessionEntity);
        when(sessionService.isSessionClosedForVoting(sessionEntity)).thenReturn(false);
        when(voteService.associateAlreadyVoted(voteRequest.getAssociateId(), voteRequest.getSessionVotingId())).thenReturn(false);
        doNothing().when(sessionService).updateVoting(sessionEntity, voteRequest);
        when(voteService.saveVote(sessionEntity.getId(), voteRequest)).thenReturn(vote);

        sessionFacade.vote(voteRequest);

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        verify(sessionService, times(1)).isSessionClosedForVoting(any());
        verify(voteService, times(1)).associateAlreadyVoted(anyLong(), anyLong());
        verify(sessionService, times(1)).updateVoting(any(), any());
        verify(voteService, times(1)).saveVote(anyLong(), any());
    }

    @Test
    void shouldNotVoteBecauseVotingSessionIsClosed() {
        VoteRequest voteRequest = VoteRequestStub.get();
        SessionEntity sessionEntity = SessionEntityStub.get();

        when(sessionService.findVotingSessionById(voteRequest.getSessionVotingId())).thenReturn(sessionEntity);
        when(sessionService.isSessionClosedForVoting(sessionEntity)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessionFacade.vote(voteRequest));

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        verify(sessionService, times(1)).isSessionClosedForVoting(any());
        verify(voteService, never()).associateAlreadyVoted(anyLong(), anyLong());
        verify(sessionService, never()).updateVoting(any(), any());
        verify(voteService, never()).saveVote(anyLong(), any());
        assertEquals("A sessão escolhida ja teve sua votação encerrada", exception.getMessage());
    }

    @Test
    void shouldNotVoteBecauseAssociateAlreadyVoted() {
        VoteRequest voteRequest = VoteRequestStub.get();
        SessionEntity sessionEntity = SessionEntityStub.get();

        when(sessionService.findVotingSessionById(voteRequest.getSessionVotingId())).thenReturn(sessionEntity);
        when(sessionService.isSessionClosedForVoting(sessionEntity)).thenReturn(false);
        when(voteService.associateAlreadyVoted(voteRequest.getAssociateId(), voteRequest.getSessionVotingId())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> sessionFacade.vote(voteRequest));

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        verify(sessionService, times(1)).isSessionClosedForVoting(any());
        verify(voteService, times(1)).associateAlreadyVoted(anyLong(), anyLong());
        verify(sessionService, never()).updateVoting(any(), any());
        verify(voteService, never()).saveVote(anyLong(), any());
        assertEquals("O Associado de ID " + voteRequest.getAssociateId()
                + " já votou na sessão de ID " + sessionEntity.getId()
                + ", só é permitido um voto por associado para cada sessão", exception.getMessage());
    }

    @Test
    void shouldCountVotesAndGetSessionVotingResultOpen() {
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotingEnd(DateParser.parseDateFromString("21-12-2023 23:59"));

        when(sessionService.findVotingSessionById(anyLong())).thenReturn(sessionEntity);

        VotingResponse voteResponse = sessionFacade.countVotesAndGetSessionVotingResult(anyLong());

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        assertEquals(VotingResultEnum.EM_ABERTO, voteResponse.getResult());
    }

    @Test
    void shouldCountVotesAndGetSessionVotingResultDraw() {
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotesForYes(1);
        sessionEntity.setVotesForNo(1);

        when(sessionService.findVotingSessionById(anyLong())).thenReturn(sessionEntity);

        VotingResponse voteResponse = sessionFacade.countVotesAndGetSessionVotingResult(anyLong());

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        assertEquals(VotingResultEnum.EMPATE, voteResponse.getResult());
    }

    @Test
    void shouldCountVotesAndGetSessionVotingResultRejected() {
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotesForYes(0);
        sessionEntity.setVotesForNo(1);

        when(sessionService.findVotingSessionById(anyLong())).thenReturn(sessionEntity);

        VotingResponse voteResponse = sessionFacade.countVotesAndGetSessionVotingResult(anyLong());

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        assertEquals(VotingResultEnum.REJEITADA, voteResponse.getResult());
    }

    @Test
    void shouldCountVotesAndGetSessionVotingResultApproved() {
        SessionEntity sessionEntity = SessionEntityStub.get();
        sessionEntity.setVotesForYes(1);
        sessionEntity.setVotesForNo(0);

        when(sessionService.findVotingSessionById(anyLong())).thenReturn(sessionEntity);

        VotingResponse voteResponse = sessionFacade.countVotesAndGetSessionVotingResult(anyLong());

        verify(sessionService, times(1)).findVotingSessionById(anyLong());
        assertEquals(VotingResultEnum.APROVADA, voteResponse.getResult());
    }
}
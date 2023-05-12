package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.entity.VoteEntity;
import com.tperuch.votingchallenge.repository.VoteRepository;
import com.tperuch.votingchallenge.stub.VoteEntityStub;
import com.tperuch.votingchallenge.stub.VoteRequestStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    void associatedAlreadyVoted() {
        when(voteRepository.existsByAssociateIdAndSessionId(1L, 1L)).thenReturn(true);
        assertTrue(voteService.associateAlreadyVoted(1L,1L));
    }

    @Test
    void associatedHaveNotVotedYet() {
        when(voteRepository.existsByAssociateIdAndSessionId(1L, 1L)).thenReturn(false);
        assertFalse(voteService.associateAlreadyVoted(1L,1L));
    }

    @Test
    void saveVote() {
        when(voteRepository.save(any())).thenReturn(VoteEntityStub.get());

        voteService.saveVote(1L, VoteRequestStub.get());

        verify(voteRepository, times(1)).save(any());
    }

}
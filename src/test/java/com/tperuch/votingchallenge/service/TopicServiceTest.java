package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.entity.TopicEntity;
import com.tperuch.votingchallenge.repository.TopicRepository;
import com.tperuch.votingchallenge.stub.TopicEntityStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @Test
    void shouldCreateTopic() {
        TopicEntity topicEntity = TopicEntityStub.get();
        topicEntity.setId(null);

        topicService.createTopic(topicEntity);

        verify(topicRepository, times(1)).save(any());
    }

    @Test
    void shouldFindTopicById() {
        when(topicRepository.findById(1L)).thenReturn(Optional.of(TopicEntityStub.get()));

        topicService.findById(1L);

        verify(topicRepository, times(1)).findById(any());
    }

    @Test
    void shouldNotFindTopicById() {
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> topicService.findById(1L));

        verify(topicRepository, times(1)).findById(any());
        assertEquals("Não foi encontrado nenhuma pauta com o id informado " + 1L, exception.getMessage());
    }

    @Test
    void shouldReturnTrueIfTopicExist() {
        when(topicRepository.existsById(1L)).thenReturn(true);
        assertTrue(topicService.existsById(1L));
    }

    @Test
    void shouldReturnFalseIfTopicDontExist() {
        when(topicRepository.existsById(1L)).thenReturn(false);
        assertFalse(topicService.existsById(1L));
    }
}
package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.stub.TopicRequestStub;
import com.tperuch.votingchallenge.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicFacadeTest {

    @Mock
    private TopicService topicService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TopicFacade topicFacade;


    @Test
    void shouldCreateTopic() {
        TopicResponse result = topicFacade.createTopic(TopicRequestStub.get());
        verify(topicService, times(1)).createTopic(any());
    }
}
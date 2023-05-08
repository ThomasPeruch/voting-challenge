package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.topic.request.TopicRequest;
import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.entity.TopicEntity;
import com.tperuch.votingchallenge.service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicFacade {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ModelMapper modelMapper;

    public TopicResponse createTopic(TopicRequest topicRequest) {
        TopicEntity topicEntity = topicService.createTopic(modelMapper.map(topicRequest, TopicEntity.class));
        return modelMapper.map(topicEntity, TopicResponse.class);
    }
}

package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.topic.request.TopicRequest;
import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.entity.TopicEntity;
import com.tperuch.votingchallenge.service.TopicService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TopicFacade {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(TopicFacade.class);

    public TopicResponse createTopic(TopicRequest topicRequest) {
        TopicEntity topicEntity = topicService.createTopic(modelMapper.map(topicRequest, TopicEntity.class));
        return modelMapper.map(topicEntity, TopicResponse.class);
    }

    public List<TopicResponse> findAllTopics() {
        List<TopicEntity> topicEntities = topicService.findAllTopics();
        if(Objects.isNull(topicEntities) || topicEntities.isEmpty()){
            logger.error("Não existe nenhuma pauta cadastrada");
            throw new EntityNotFoundException("Não existe nenhuma pauta cadastrada");
        }
        return topicEntities.stream()
                .map(entity -> modelMapper.map(entity, TopicResponse.class)).collect(Collectors.toList());
    }
}

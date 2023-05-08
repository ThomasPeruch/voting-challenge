package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.entity.TopicEntity;
import com.tperuch.votingchallenge.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public TopicEntity createTopic(TopicEntity topicEntity) {
        return topicRepository.save(topicEntity);
    }
}

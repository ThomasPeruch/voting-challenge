package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.entity.TopicEntity;
import com.tperuch.votingchallenge.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<TopicEntity> findAllTopics() {
        return topicRepository.findAll();
    }

    public TopicEntity createTopic(TopicEntity topicEntity) {
        return topicRepository.save(topicEntity);
    }

    public TopicEntity findById(Long id){
        return topicRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Não foi encontrado nenhuma pauta com o id informado " + id));
    }

    public boolean existsById(Long id){
        return topicRepository.existsById(id);
    }
}

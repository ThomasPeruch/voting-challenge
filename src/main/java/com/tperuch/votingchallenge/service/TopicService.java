package com.tperuch.votingchallenge.service;

import com.tperuch.votingchallenge.entity.TopicEntity;
import com.tperuch.votingchallenge.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TopicService {

    Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    private TopicRepository topicRepository;

    public List<TopicEntity> findAllTopics() {
        logger.info("Buscando todas as pautas");
        return topicRepository.findAll();
    }

    public TopicEntity createTopic(TopicEntity topicEntity) {
        logger.info("Salvando nova pauta");
        return topicRepository.save(topicEntity);
    }

    public TopicEntity findById(Long id){
        logger.info("Buscando pauta de id:{}",id);
        return topicRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Não foi encontrado nenhuma pauta com o id informado " + id));
    }

    public boolean existsById(Long id){
        return topicRepository.existsById(id);
    }
}

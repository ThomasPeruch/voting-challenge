package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.entity.TopicEntity;

public class TopicEntityStub {
    public static TopicEntity get(){
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setDescription("description");
        topicEntity.setId(1L);
        return topicEntity;
    }
}

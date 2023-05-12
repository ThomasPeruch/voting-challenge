package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.topic.request.TopicRequest;

public class TopicRequestStub {
    public static TopicRequest get(){
        return new TopicRequest("description");
    }
}

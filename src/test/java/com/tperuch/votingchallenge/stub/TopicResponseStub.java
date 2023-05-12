package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;

public class TopicResponseStub {
    public static TopicResponse get() {
        return new TopicResponse(1L, "description");
    }
}

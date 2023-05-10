package com.tperuch.votingchallenge.controller.topic;

import com.tperuch.votingchallenge.controller.config.ErrorMessage;
import com.tperuch.votingchallenge.controller.topic.request.TopicRequest;
import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.facade.TopicFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    private TopicFacade topicFacade;

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest topicRequest){
        return new ResponseEntity(topicFacade.createTopic(topicRequest), HttpStatus.CREATED);
    }
}

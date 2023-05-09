package com.tperuch.votingchallenge.facade;

import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.service.SessionService;
import com.tperuch.votingchallenge.service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class SessionFacade {

    @Autowired
    private TopicService topicService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ModelMapper modelMapper;

    public SessionResponse openVotingSession(Long topicId, SessionRequest sessionRequest) {
        if (!topicService.existsById(topicId)) {
            throw new EntityNotFoundException("Não foi encontrado nenhuma pauta com o id informado - ID: " + topicId);
        }
        return modelMapper.map(sessionService.saveVotingSession(topicId, sessionRequest.getVotingEnd()), SessionResponse.class);
    }
}

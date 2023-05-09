package com.tperuch.votingchallenge.controller.session;

import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.facade.SessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    private SessionFacade sessionFacade;

    @PostMapping(value = "/{topicId}")
    public ResponseEntity<SessionResponse> openVotingSession(@PathVariable Long topicId, @Valid @RequestBody SessionRequest sessionRequest) {
        return new ResponseEntity(sessionFacade.openVotingSession(topicId, sessionRequest), HttpStatus.CREATED);
    }
}

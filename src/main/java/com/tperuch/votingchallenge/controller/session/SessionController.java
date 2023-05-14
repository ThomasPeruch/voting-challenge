package com.tperuch.votingchallenge.controller.session;

import com.tperuch.votingchallenge.controller.handler.ErrorMessage;
import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.controller.session.response.SessionResponse;
import com.tperuch.votingchallenge.controller.session.response.VoteResponse;
import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.facade.SessionFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Abre uma sessão de votação para uma pauta", produces = "application/json", code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sessão de votação criada com sucesso", response = SessionResponse.class),
            @ApiResponse(code = 400, message = "Dados informados são inválidos", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Recurso não encontrado", response = ErrorMessage.class)
    })
    public ResponseEntity<SessionResponse> openVotingSession(@PathVariable Long topicId, @Valid @RequestBody SessionRequest sessionRequest) {
        return new ResponseEntity(sessionFacade.openVotingSession(topicId, sessionRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/vote")
    @ApiOperation(value = "Vota em uma sessão", produces = "application/json", code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Voto registrado com sucesso", response = VoteResponse.class),
            @ApiResponse(code = 400, message = "Dados informados são inválidos", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Recurso não encontrado", response = ErrorMessage.class)
    })
    public ResponseEntity<VoteResponse> vote(@Valid @RequestBody VoteRequest voteRequest){
        return new ResponseEntity(sessionFacade.vote(voteRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/count-votes/{sessionId}")
    @ApiOperation(value = "Conta votos e exibe resultado da sessão de votos", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = VoteResponse.class),
            @ApiResponse(code = 400, message = "Dados informados são inválidos", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Recurso não encontrado", response = ErrorMessage.class)
    })
    public ResponseEntity<VoteResponse> countVotesAndGetSessionVotingResult(@PathVariable Long sessionId){
        return new ResponseEntity(sessionFacade.countVotesAndGetSessionVotingResult(sessionId), HttpStatus.OK);
    }
}

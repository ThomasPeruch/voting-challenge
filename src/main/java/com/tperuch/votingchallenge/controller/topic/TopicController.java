package com.tperuch.votingchallenge.controller.topic;

import com.tperuch.votingchallenge.controller.handler.ErrorMessage;
import com.tperuch.votingchallenge.controller.topic.request.TopicRequest;
import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.facade.TopicFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    private TopicFacade topicFacade;

    @PostMapping
    @ApiOperation(value = "Cria uma pauta para votação", produces = "application/json", code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pauta criado com sucesso", response = TopicResponse.class),
            @ApiResponse(code = 400, message = "Dados informados são inválidos", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Recurso não encontrado", response = ErrorMessage.class)
    })
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest topicRequest) {
        return new ResponseEntity(topicFacade.createTopic(topicRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Busca todas as pautas para votação", produces = "application/json", code = 200)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta criado com sucesso", response = TopicResponse.class),
            @ApiResponse(code = 404, message = "Recurso não encontrado", response = ErrorMessage.class)
    })
    public ResponseEntity<TopicResponse> findAllTopics() {
        return new ResponseEntity(topicFacade.findAllTopics(), HttpStatus.OK);
    }
}

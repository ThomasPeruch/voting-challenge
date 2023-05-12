package com.tperuch.votingchallenge.controller.session;

import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.controller.session.request.VoteRequest;
import com.tperuch.votingchallenge.facade.SessionFacade;
import com.tperuch.votingchallenge.stub.*;
import com.tperuch.votingchallenge.utils.DateParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
@AutoConfigureMockMvc
class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SessionFacade sessionFacade;

    @Test
    void getVotesAndSessionResult() throws Exception {
        Long sessionId = 1L;
        when(sessionFacade.countVotesAndGetSessionVotingResult(sessionId)).thenReturn(VotingResponseStub.getApprovedVoting());

        MvcResult result = mvc.perform(get("http://localhost:8080/session/count-votes/" + sessionId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("\"sessionId\":" + sessionId));
    }

    @Test
    void shouldCreateDateFormatted() throws Exception {
        Long topicId = 1L;
        SessionRequest sessionRequest = SessionRequestStub.get();
        sessionRequest.setVotingEnd(DateParser.parseDateFromString("05-10-2023 10:10"));

        when(sessionFacade.openVotingSession(topicId, sessionRequest)).thenReturn(SessionResponseStub.get());

        String sessionVotingRequest = "{\"votingEnd\":\"05-10-2023 10:10\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/session/" + topicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionVotingRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String requestDateFormatted = sessionVotingRequest.replace("{", "").replace("}", "");

        assertTrue(content.contains(requestDateFormatted));
        assertTrue(content.contains("\"topicId\":" + topicId));
    }

    @Test
    void shouldVote() throws Exception {
        VoteRequest voteRequest = VoteRequestStub.get();
        String voteRequestJson =
                "{\"associateId\":1," +
                        "\"voteValue\":\"SIM\"," +
                        "\"sessionVotingId\":1}";


        when(sessionFacade.vote(voteRequest)).thenReturn(VoteResponseStub.get());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/session/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String requestVoteFormatted = voteRequestJson.replace("{", "")
                .replace("}", "")
                .replace("sessionVotingId", "sessionId");


        assertTrue(content.contains(requestVoteFormatted));
    }
}
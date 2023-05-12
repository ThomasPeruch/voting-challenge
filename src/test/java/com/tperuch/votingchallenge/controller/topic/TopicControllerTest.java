package com.tperuch.votingchallenge.controller.topic;

import com.tperuch.votingchallenge.controller.topic.request.TopicRequest;
import com.tperuch.votingchallenge.controller.topic.response.TopicResponse;
import com.tperuch.votingchallenge.facade.TopicFacade;
import com.tperuch.votingchallenge.stub.TopicRequestStub;
import com.tperuch.votingchallenge.stub.TopicResponseStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TopicController.class)
@AutoConfigureMockMvc
class TopicControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TopicFacade topicFacade;

    @Test
    void createTopic() throws Exception {
        String topicRequestJson = "{\"description\":\"description\"}";
        TopicRequest topicRequest = TopicRequestStub.get();

        when(topicFacade.createTopic(topicRequest)).thenReturn(TopicResponseStub.get());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/topic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(topicRequestJson))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String requestFormattedForComparison = topicRequestJson
                .replace("{","")
                .replace("}","");

        assertTrue(responseBody.contains(requestFormattedForComparison));
    }

    @Test
    void shouldThrowExceptionWhenJsonFieldIsEmpty() throws Exception {
        String topicRequestJson = "{\"description\":\"\"}";
        TopicRequest topicRequest = TopicRequestStub.get();

        when(topicFacade.createTopic(topicRequest)).thenReturn(TopicResponseStub.get());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicRequestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(exception -> assertTrue(exception.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);
    }

    @Test
    void shouldThrowExceptionNoRequestBodyIsSent() throws Exception {
        TopicRequest topicRequest = TopicRequestStub.get();

        when(topicFacade.createTopic(topicRequest)).thenReturn(TopicResponseStub.get());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/topic")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(exception -> assertTrue(exception.getResolvedException() instanceof HttpMessageNotReadableException))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);
    }
}
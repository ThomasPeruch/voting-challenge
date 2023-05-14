package com.tperuch.votingchallenge.controller.session.response;

import com.tperuch.votingchallenge.controller.session.enums.SessionStatusEnum;
import io.swagger.annotations.ApiModelProperty;

public class SessionStatusResponse {

    @ApiModelProperty(value = "Id da sessão de votação")
    private Long id;

    @ApiModelProperty(value = "Id da pauta")
    private Long topicId;

    @ApiModelProperty(value = "Status da sessão de votação")
    private SessionStatusEnum status;

    public SessionStatusResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public SessionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SessionStatusEnum status) {
        this.status = status;
    }
}

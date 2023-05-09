package com.tperuch.votingchallenge.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_voting")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long topicId;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime votingStart;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime votingEnd;

    private Integer votesForYes;

    private Integer votesForNo;

    public SessionEntity() {
    }

    public SessionEntity(Long id, Long topicId, LocalDateTime votingStart, LocalDateTime votingEnd, Integer votesForYes, Integer votesForNo) {
        this.id = id;
        this.topicId = topicId;
        this.votingStart = votingStart;
        this.votingEnd = votingEnd;
        this.votesForYes = votesForYes;
        this.votesForNo = votesForNo;
    }

    public SessionEntity(Long topicId, LocalDateTime votingStart, LocalDateTime votingEnd) {
        this.topicId = topicId;
        this.votingStart = votingStart;
        this.votingEnd = votingEnd;
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

    public LocalDateTime getVotingStart() {
        return votingStart;
    }

    public void setVotingStart(LocalDateTime votingStart) {
        this.votingStart = votingStart;
    }

    public LocalDateTime getVotingEnd() {
        return votingEnd;
    }

    public void setVotingEnd(LocalDateTime votingEnd) {
        this.votingEnd = votingEnd;
    }

    public Integer getVotesForYes() {
        return votesForYes;
    }

    public void setVotesForYes(Integer votesForYes) {
        this.votesForYes = votesForYes;
    }

    public Integer getVotesForNo() {
        return votesForNo;
    }

    public void setVotesForNo(Integer votesForNo) {
        this.votesForNo = votesForNo;
    }
}

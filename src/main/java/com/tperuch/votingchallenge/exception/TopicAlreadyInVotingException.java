package com.tperuch.votingchallenge.exception;

public class TopicAlreadyInVotingException extends RuntimeException{
    public TopicAlreadyInVotingException(String message) {
        super(message);
    }
}

package com.tperuch.votingchallenge.exception;

public class SessionClosedException extends RuntimeException{
    public SessionClosedException(String message) {
        super(message);
    }
}

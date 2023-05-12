package com.tperuch.votingchallenge.exception;

public class AssociateAlreadyVotedException extends RuntimeException{
    public AssociateAlreadyVotedException(String message) {
        super(message);
    }
}

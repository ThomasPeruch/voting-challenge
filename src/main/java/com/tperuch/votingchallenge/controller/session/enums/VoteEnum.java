package com.tperuch.votingchallenge.controller.session.enums;

import java.util.Arrays;

public enum VoteEnum {
    SIM(true),
    NAO(false);

    private final Boolean voteValue;

    VoteEnum(Boolean voteValue) {
        this.voteValue = voteValue;
    }

    public Boolean getVoteValue() {
        return voteValue;
    }

    public static VoteEnum getVoteEnumByValue(boolean value){
        return Arrays.stream(VoteEnum.values())
                .filter(vote -> vote.voteValue.equals(value))
                .findFirst()
                .orElse(null);
    }
}

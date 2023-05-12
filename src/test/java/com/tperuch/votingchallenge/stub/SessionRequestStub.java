package com.tperuch.votingchallenge.stub;

import com.tperuch.votingchallenge.controller.session.request.SessionRequest;
import com.tperuch.votingchallenge.utils.DateParser;

public class SessionRequestStub {

    public static SessionRequest get() {
        return new SessionRequest(DateParser.parseDateFromString("10-10-2023 10:10"));
    }
}

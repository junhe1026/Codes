package com.acmetelecom;

import com.acmetelecom.platform.Clock;

public class CallStart extends CallEvent {
    public CallStart(String caller, String callee, Clock clock) {
        super(caller, callee, clock.currentTimeMillis());
    }
}

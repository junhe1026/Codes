package com.acmetelecom.test;

import static org.junit.Assert.assertEquals;

import com.acmetelecom.platform.Clock;
import com.acmetelecom.platform.SystemClock;
import org.junit.Test;

/**
 * Simple tests for the system clock. Just make sure that
 * that our system clock returns the current system time.
 * This test might fail if the system where it is run is 
 * particularly slow. The call to currentTimeMillis() in
 * the assertion does not necessarily happen on the same
 * millisecond as inside the clock.
 */
public class SystemClockTest {
    @Test
    public void testCurrentTimeMillis() throws Exception {
        Clock clock = new SystemClock();
        assertEquals(System.currentTimeMillis(),clock.currentTimeMillis());
    }
}

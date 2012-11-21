package com.acmetelecom.fakes;

import java.util.Date;
import java.util.Calendar;

import com.acmetelecom.builders.Hour;
import com.acmetelecom.builders.Minute;
import com.acmetelecom.builders.Second;
import com.acmetelecom.platform.Clock;

/**
 * Fake clock implementation. This class can be initialised to a base time.
 * From that point on, the clock can be moved forward a relative amount of 
 * time or simply jump to a time in the future. This is helpful for testing 
 * purposes, each scenario can be described based on a fixed start time.
 * The clock can then be moved forward as much as necessary and many times
 * as needed to simulate any specific test scenario. This class is used
 * by unit-tests and end-to-end acceptance tests. 
 */
public class FakeClock implements Clock {
	private long baseMillis = 0;
	private long currMillis = 0;
	
	/**
	 * Convenience constructor. Expects the initial/base time for the clock. 
	 * @param testDate Start time for the clock
	 */
	public FakeClock(Date testDate) {
		this.baseMillis = testDate.getTime();
		this.currMillis = baseMillis;
	}

	/**
	 * Convenience constructor. Expects the initial/base time for the clock. 
	 * @param calendar Start time for the clock (as a Calendar object)
	 */
	public FakeClock(Calendar calendar) {
		this.baseMillis = calendar.getTimeInMillis(); 
		this.currMillis = baseMillis;
	}
	
	/**
	 * Method defined by the Clock interface. This method simply returns
	 * the current time (calculated by the fake clock depending on the base
	 * or starting time and the methods that were called subsequently).  
	 */
	@Override
	public long currentTimeMillis() {
		return this.currMillis;
	}
	
	/**
	 * Move the clock forward a given numbers of hours.
	 */
	public void moveForward(Hour hour) {
		this.currMillis += hour.getHour() * 60 * 60 * 1000;
	}

	/**
	 * Move the clock forward a given numbers of minutes.
	 */
	public void moveForward(Minute minute) {
		this.currMillis += minute.getMinute() * 60 * 1000;
	}

	/**
	 * Move the clock forward a given numbers of seconds.
	 */
	public void moveForward(Second second) {
		this.currMillis += second.getSecond() * 1000;
	}

	/**
	 * Move the clock forward to a specific time (using a calendar object).
	 */
	public void moveTo(Calendar calendar) {
		this.currMillis = calendar.getTimeInMillis();
	}
	
	@Override
	public String toString() {
		return "baseMillis=" + baseMillis + " currMillis=" + currMillis;
	} 
}

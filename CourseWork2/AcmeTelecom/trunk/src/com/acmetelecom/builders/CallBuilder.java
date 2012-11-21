package com.acmetelecom.builders;

import java.util.Date;

import com.acmetelecom.Call;
import com.acmetelecom.CallEvent;
import com.acmetelecom.customer.Customer;
import static com.acmetelecom.builders.Second.Seconds;

public class CallBuilder implements Builder {
	private String caller;
	private String callee;
	private CallEvent start;
	private CallEvent end;
	private int duration;
	
	public static CallBuilder aCall() {
		return new CallBuilder();
	}
	
	private CallBuilder() {
		this.callee = "";
		this.caller = "";
		
		this.start = null;
		this.end = null;
	}
	
	public CallBuilder from(Customer caller) {
		this.caller = caller.getPhoneNumber();
		return this;
	}
	
	public CallBuilder to(Customer callee) {
		this.callee = callee.getPhoneNumber();
		return this;
	}

	public CallBuilder startingNow() {
		this.start = new CallEvent(this.caller, this.callee, System.currentTimeMillis());
		return this;
	}

	public CallBuilder endingNow() {
		this.end = new CallEvent(this.caller, this.callee, System.currentTimeMillis());
		return this;
	}
	
	public CallBuilder startingAt(Date date) {
		this.start = new CallEvent(this.caller, this.callee, date.getTime());
		return this;
	}

	public CallBuilder endingAt(Date date) {
		this.end = new CallEvent(this.caller, this.callee, date.getTime());
		return this;
	}

	public CallBuilder lastingFor(Hour hour) {
		return this.lastingFor(Seconds(hour.getHour() * 60 * 60));
	}

	public CallBuilder lastingFor(Minute minute) {
		return this.lastingFor(Seconds(minute.getMinute() * 60));
	}
	
	public CallBuilder lastingFor(Second second) {
		// For the time being, this is just seconds. We will eventually use value types
		this.duration = second.getSecond() * 1000;

		this.end = new CallEvent(this.caller, this.callee, this.start.time() + this.duration);
		return this;
	}
	
	@Override
	public Call build() {
		return new Call(this.start, this.end);
	}

}

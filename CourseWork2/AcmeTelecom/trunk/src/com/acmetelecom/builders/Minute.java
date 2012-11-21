package com.acmetelecom.builders;

public class Minute {
	private int minute;
	
	public static Minute Minutes(int minutes) {
		return new Minute(minutes);
	}
	
	private Minute(int minute) {
		this.minute = minute;
	}
	
	public int getMinute() {
		return this.minute;
	}
}

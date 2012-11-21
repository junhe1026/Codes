package com.acmetelecom.builders;

public class Second {
	private int second;
	
	public static Second Seconds(int seconds) {
		return new Second(seconds);
	}
	
	private Second(int second) {
		this.second = second;
	}
	
	public int getSecond() {
		return this.second;
	}
}

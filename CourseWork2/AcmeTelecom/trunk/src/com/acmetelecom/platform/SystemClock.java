package com.acmetelecom.platform;


public class SystemClock implements Clock {
	@Override
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}

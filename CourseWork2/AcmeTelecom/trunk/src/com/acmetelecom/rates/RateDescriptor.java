package com.acmetelecom.rates;

import java.math.BigDecimal;

public class RateDescriptor {
	private BigDecimal rate;
	private int seconds;
	
	public RateDescriptor(BigDecimal rate, int seconds) {
		this.rate = rate;
		this.seconds = seconds;
	}

	public int getSeconds() {
		return seconds;
	}

	public BigDecimal getRate() {
		return rate;
	}
}

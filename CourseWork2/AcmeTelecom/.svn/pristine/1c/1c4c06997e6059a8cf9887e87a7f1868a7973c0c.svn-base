package com.acmetelecom.rates;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Tariff;

public class OffpeakRateSelector implements RateSelector {
	private int startHour = 7;
	private int endHour = 19;
	
	public OffpeakRateSelector(int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}
	
    private boolean isOffPeak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < this.startHour || hour >= this.endHour;
    }

	@Override
	public Collection<RateDescriptor> determineRates(Tariff tariff, Call call) {
		Collection<RateDescriptor> rates = new LinkedList<RateDescriptor>();

        // For the time being, we will just apply one rate for the 
		// entire call, to keep the code working as it was before.
        int duration = call.durationSeconds();
		BigDecimal rate;

        if(isOffPeak(call.startTime()) && isOffPeak(call.endTime()) && duration < 12 * 60 * 60) {
            rate = tariff.offPeakRate();
        } else {
            rate = tariff.peakRate();
        }
        
		rates.add(new RateDescriptor(rate, duration));
		return rates;
	}
}

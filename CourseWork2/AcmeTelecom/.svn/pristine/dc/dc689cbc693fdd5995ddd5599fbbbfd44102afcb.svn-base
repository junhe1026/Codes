package com.acmetelecom.test;

import static com.acmetelecom.builders.CallBuilder.aCall;
import static com.acmetelecom.builders.CustomerBuilder.aCustomer;
import static com.acmetelecom.builders.Hour.Hours;
import static com.acmetelecom.builders.Minute.Minutes;
import static com.acmetelecom.builders.Second.Seconds;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.builders.Hour;
import com.acmetelecom.builders.Minute;
import com.acmetelecom.builders.Second;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.rates.OffpeakRateSelector;
import com.acmetelecom.rates.RateDescriptor;


public class OffpeakRateSelectorTest {
	final Customer customer1 = aCustomer().
							named("John Smith").
							withPhoneNumber("447766406373").
							andPricePlan(Tariff.Standard.toString()).build();

	final Customer customer2 = aCustomer().
							named("Sarah Collins").
							withPhoneNumber("447652334879").
							andPricePlan(Tariff.Standard.toString()).build();
	
	private Date buildStartTime(Hour hour, Minute minute, Second second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour.getHour());
		calendar.set(Calendar.MINUTE, minute.getMinute());
		calendar.set(Calendar.SECOND, second.getSecond());
		
		return calendar.getTime();
	}

	@Test
	public void testNormalOffpeakHoursRatesAreReturned() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(15),Minutes(30),Seconds(0))).
						endingAt(buildStartTime(Hours(15),Minutes(45),Seconds(0))).build();
		
		OffpeakRateSelector selector = new OffpeakRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		assertFalse("No rates found for " + Tariff.Standard, rates == null);
		assertFalse("No rates found for " + Tariff.Standard, rates.size() == 0);
	}
	
	@Test
	public void testNormalOffpeakHoursValidDuration() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(10),Minutes(50),Seconds(0))).
						endingAt(buildStartTime(Hours(11),Minutes(05),Seconds(0))).build();
		
		OffpeakRateSelector selector = new OffpeakRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		RateDescriptor descriptor = rates.iterator().next();
		assertEquals(descriptor.getSeconds(), call.durationSeconds());		
	}
	
	@Test
	public void testNormalOffpeakHoursWithFullOnpeakCall() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(15),Minutes(30),Seconds(0))).
						endingAt(buildStartTime(Hours(15),Minutes(45),Seconds(0))).build();
		
		OffpeakRateSelector selector = new OffpeakRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		RateDescriptor descriptor = rates.iterator().next();
		assertTrue(descriptor.getRate().equals(Tariff.Standard.peakRate()));
	}
	
	@Test
	public void testNormalOffpeakHoursWithFullOffpeakCall() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(19),Minutes(00),Seconds(0))).
						endingAt(buildStartTime(Hours(19),Minutes(00),Seconds(45))).build();
		
		OffpeakRateSelector selector = new OffpeakRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		RateDescriptor descriptor = rates.iterator().next();
		assertTrue(descriptor.getRate().equals(Tariff.Standard.offPeakRate()));
	}

	@Test
	public void testNormalOffpeakHoursWithOverlappingCallAtStart() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(6),Minutes(55),Seconds(0))).
						endingAt(buildStartTime(Hours(7),Minutes(5),Seconds(0))).build();
		
		OffpeakRateSelector selector = new OffpeakRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Business, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		RateDescriptor descriptor = rates.iterator().next();
		assertTrue(descriptor.getRate().equals(Tariff.Business.peakRate()));
	}
	
	@Test
	public void testNormalOffpeakHoursWithOverlappingCallAtEnd() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(18),Minutes(55),Seconds(0))).
						endingAt(buildStartTime(Hours(19),Minutes(5),Seconds(0))).build();
		
		OffpeakRateSelector selector = new OffpeakRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Business, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		RateDescriptor descriptor = rates.iterator().next();
		assertTrue(descriptor.getRate().equals(Tariff.Business.peakRate()));		
	}
}

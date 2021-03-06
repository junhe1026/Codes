package com.acmetelecom.test;

import static com.acmetelecom.builders.CallBuilder.aCall;
import static com.acmetelecom.builders.CustomerBuilder.aCustomer;
import static com.acmetelecom.builders.Hour.Hours;
import static com.acmetelecom.builders.Minute.Minutes;
import static com.acmetelecom.builders.RateDescriptorBuilder.aRateDescriptor;
import static com.acmetelecom.builders.Second.Seconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.builders.Hour;
import com.acmetelecom.builders.Minute;
import com.acmetelecom.builders.Second;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.rates.OffpeakFairRateSelector;
import com.acmetelecom.rates.RateDescriptor;

public class OffpeakFairRateSelectorTest {
	final Customer customer1 = aCustomer().
							named("John Smith").
							withPhoneNumber("447766406373").
							andPricePlan(Tariff.Standard.toString()).build();

	final Customer customer2 = aCustomer().
							named("Sarah Collins").
							withPhoneNumber("447652334879").
							andPricePlan(Tariff.Standard.toString()).build();
	
	// Matcher that tests the equality of a rate descriptor
	private static org.hamcrest.Matcher<RateDescriptor> equalsTo(RateDescriptor rate)
	{
		return IsEqual.<RateDescriptor>equalTo(rate);
	}
	
	// Matcher for an iterable collection of rate descriptors. The idea is to match 
	// any descriptor in the collection with matcher m. Want to make assertions fluent and readable
	private static Matcher<Iterable<? super RateDescriptor>> anyRateDescriptor(Matcher<RateDescriptor> m) {
		return org.hamcrest.core.IsCollectionContaining.<RateDescriptor>hasItem(m);
	}
	
	private Date buildStartTime(Hour hour, Minute minute, Second second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour.getHour());
		calendar.set(Calendar.MINUTE, minute.getMinute());
		calendar.set(Calendar.SECOND, second.getSecond());
		calendar.set(Calendar.MILLISECOND, 0);
		
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
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
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
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
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
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		assertThat(	rates, 
				anyRateDescriptor(equalsTo(
					aRateDescriptor().
					charging(Tariff.Standard.peakRate()).
					withDuration(Minutes(15)).build())));
	}
	
	@Test
	public void testNormalOffpeakHoursWithFullOffpeakCall() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(19),Minutes(00),Seconds(1))).
						endingAt(buildStartTime(Hours(19),Minutes(00),Seconds(46))).build();
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		assertThat(	rates, 
						anyRateDescriptor(equalsTo(
							aRateDescriptor().
							charging(Tariff.Standard.offPeakRate()).
							withDuration(Seconds(45)).build())));
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
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Business, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 2);
		
		// The call lasts for ten minutes, we should get 5 minutes of on-peak
		// and 5 minutes of off-peak rate. First check that we get the correct number of rates
		assertThat(	rates, 
					anyRateDescriptor(equalsTo(
							aRateDescriptor().
							charging(Tariff.Business.offPeakRate()).
							withDuration(Seconds(300)).build()))); // This is half call: 5 minutes (300 seconds)
		
		assertThat(	rates, 
				anyRateDescriptor(equalsTo(
						aRateDescriptor().
						charging(Tariff.Business.peakRate()).
						withDuration(Seconds(300)).build())));	// This is half call: 5 minutes (300 seconds)
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
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Standard, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 2);
		
		// The call lasts for ten minutes, we should get 5 minutes of on-peak
		// and 5 minutes of off-peak rate. First check that we get the correct number of rates
		assertThat(	rates, 
					anyRateDescriptor(equalsTo(
							aRateDescriptor().
							charging(Tariff.Standard.offPeakRate()).
							withDuration(Minutes(5)).build()))); // This is half call: 5 minutes (300 seconds)
		
		assertThat(	rates, 
				anyRateDescriptor(equalsTo(
						aRateDescriptor().
						charging(Tariff.Standard.peakRate()).
						withDuration(Minutes(5)).build())));	// This is half call: 5 minutes (300 seconds)	}
	}
	
	@Test
	public void testNormalOffpeakHoursWithCallEncompassingEntireOnpeak() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(6),Minutes(30),Seconds(0))).
						endingAt(buildStartTime(Hours(19),Minutes(30),Seconds(0))).build();
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Business, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 2);
		
		// The call lasts for ten minutes, we should get 5 minutes of on-peak
		// and 5 minutes of off-peak rate. First check that we get the correct number of rates
		assertThat(	rates, 
					anyRateDescriptor(equalsTo(
							aRateDescriptor().
							charging(Tariff.Business.offPeakRate()).
							withDuration(Hours(1)).build()))); // Only one hour of offpeak call
		
		assertThat(	rates, 
				anyRateDescriptor(equalsTo(
						aRateDescriptor().
						charging(Tariff.Business.peakRate()).
						withDuration(Hours(12)).build())));	// 12 hours of onpeak call
	}
	
	@Test
	public void testNormalOffpeakHoursWithCallEncompassingEntireOffpeakBoundary() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(6),Minutes(50),Seconds(0))).
						endingAt(buildStartTime(Hours(7),Minutes(00),Seconds(0))).build();
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Business, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		// The call lasts for ten minutes, we should get 10 minutes of off-peak
		assertThat(	rates, 
					anyRateDescriptor(equalsTo(
							aRateDescriptor().
							charging(Tariff.Business.offPeakRate()).
							withDuration(Minutes(10)).build())));
	}
	
	@Test
	public void testNormalOffpeakHoursWithCallEncompassingEntireOnpeakBoundary() {
		int startHour = 7;
		int endHour = 19;
		
		Call call = aCall().
						from(customer1).
						to(customer2).
						startingAt(buildStartTime(Hours(7),Minutes(00),Seconds(0))).
						endingAt(buildStartTime(Hours(7),Minutes(10),Seconds(0))).build();
		
		OffpeakFairRateSelector selector = new OffpeakFairRateSelector(startHour, endHour);
		Collection<RateDescriptor> rates = selector.determineRates(Tariff.Business, call);
		
		// Check that the totality of the call is charged at the appropriate rate
		assertTrue("There should be one rate", rates.size() == 1);
		
		// The call lasts for ten minutes, we should get 5 minutes of on-peak
		// and 5 minutes of off-peak rate. First check that we get the correct number of rates
		assertThat(	rates, 
					anyRateDescriptor(equalsTo(
							aRateDescriptor().
							charging(Tariff.Business.peakRate()).
							withDuration(Minutes(10)).build())));
	}	
	
}

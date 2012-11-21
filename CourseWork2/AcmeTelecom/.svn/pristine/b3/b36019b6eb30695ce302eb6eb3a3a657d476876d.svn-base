package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Customer;
import static com.acmetelecom.builders.CustomerBuilder.aCustomer;
import static com.acmetelecom.builders.Second.Seconds;
import static com.acmetelecom.builders.Hour.Hours;
import static com.acmetelecom.builders.Minute.Minutes;
import static com.acmetelecom.builders.CallBuilder.aCall;

public class CallBuilderTest {

	@Test
	public void testACallWithCustomers() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Call c= aCall().from(cus1).to(cus2).startingNow().lastingFor(Seconds(10)).build();
		assertEquals(c.callee(),"447920434499");
		
	}
	
	@Test
	public void testACallWithDuration() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Call c= aCall().from(cus1).to(cus2).startingNow().lastingFor(Seconds(10)).build();
		assertEquals(c.durationSeconds(),10);
		
	}

    @Test
	public void testACallWithDurationUsingEndingNow() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build();
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
        Date date=new Date();
		date.setTime(System.currentTimeMillis()-Seconds(20).getSecond()*1000);
		Call c= aCall().from(cus1).to(cus2).startingAt(date).endingNow().build();
		assertEquals(c.durationSeconds(),20);

	}

	@Test
	public void testACallWithDate() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(Seconds(10)).build();
		assertEquals(c.date(),SimpleDateFormat.getInstance().format(date));
		
	}
	
	@Test
	public void testACallWithStartingTime() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(Seconds(10)).build();
		assertEquals(c.startTime(),date);
		
	}
	
	@Test
	public void testACallWithEndingTimeUsingSeconds() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(Seconds(100)).build();
		date.setTime(date.getTime()+100000);
		assertEquals(c.endTime(),date);
		
	}

    @Test
	public void testACallWithEndingTimeUsingMinutes() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build();
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(Minutes(2)).build();
		date.setTime(date.getTime()+Minutes(2).getMinute()*60*1000);
		assertEquals(c.endTime(),date);

	}

    @Test
	public void testACallWithEndingTimeUsingHours() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build();
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(Hours(3)).build();
		date.setTime(date.getTime()+Hours(3).getHour()*60*60*1000);
		assertEquals(c.endTime(),date);

	}
}
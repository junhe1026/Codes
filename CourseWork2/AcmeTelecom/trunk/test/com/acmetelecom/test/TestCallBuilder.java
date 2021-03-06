package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.customer.Customer;
import static com.acmetelecom.builders.CustomerBuilder.aCustomer;

import static com.acmetelecom.builders.CallBuilder.aCall;

public class TestCallBuilder {

	@Test
	public void testACallWithCustomers() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Call c= aCall().from(cus1).to(cus2).startingNow().lastingFor(10).build();
		assertEquals(c.callee(),"447920434499");
		
	}
	
	@Test
	public void testACallWithDuration() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Call c= aCall().from(cus1).to(cus2).startingNow().lastingFor(10).build();
		assertEquals(c.durationSeconds(),10);
		
	}

	@Test
	public void testACallWithDate() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(10).build();
		assertEquals(c.date(),SimpleDateFormat.getInstance().format(date));
		
	}
	
	@Test
	public void testACallWithStartingTime() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(100).build();
		assertEquals(c.startTime(),date);
		
	}
	
	@Test
	public void testACallWithEndingTime() {
		Customer cus1=aCustomer().named("John").withPhoneNumber("447766406373").build(); 
		Customer cus2=aCustomer().named("Bob").withPhoneNumber("447920434499").build();
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		Call c= aCall().from(cus1).to(cus2).startingAt(date).lastingFor(100).build();
		date.setTime(date.getTime()+100000);
		assertEquals(c.endTime(),date);
		
	}
}

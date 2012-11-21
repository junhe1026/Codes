package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.acmetelecom.Call;
import com.acmetelecom.CallEvent;
import com.acmetelecom.LineItem;

import org.junit.Test;

import static com.acmetelecom.builders.LineItemBuilder.aLineItem;

public class LineItemBuilderTest {

	@Test
	public void testALineItemForCall() {
		CallEvent start = new CallEvent("4894613648979", "447766406373", 1000);
		CallEvent end = new CallEvent("4894613648979", "447766406373", 3000);
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = aLineItem().forCall(call).withACostOf(callCost).build();
		assertEquals(l.callee(), start.getCallee());
	}
	
	@Test
	public void testALineItemForDate(){
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		CallEvent start = new CallEvent("4894613648979", "447766406373", date.getTime());
		CallEvent end = new CallEvent("4894613648979", "447766406373", date.getTime()+1000);
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = aLineItem().forCall(call).withACostOf(callCost).build();
		assertEquals(l.date(),SimpleDateFormat.getInstance().format( date));
	}
	
	@Test 
	public void testALineItemForDurationMinutes(){
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		CallEvent start = new CallEvent("4894613648979", "447766406373", date.getTime());
		CallEvent end = new CallEvent("4894613648979", "447766406373", date.getTime()+1000);
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = aLineItem().forCall(call).withACostOf(callCost).build();
		assertEquals(l.durationMinutes(),("" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60)));
	}
	
	@Test 
	public void testALineItemWithACostOf() {
		BigDecimal callCost = new BigDecimal(1000);	
		LineItem l = aLineItem().withACostOf(callCost).build();
		assertEquals(l.cost(), callCost);
		
	}
}

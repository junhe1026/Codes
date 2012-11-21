package com.acmetelecom.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.acmetelecom.Call;
import com.acmetelecom.CallEvent;
import com.acmetelecom.LineItem;

public class LineItemTest {

	@Test
	public void testDate() {
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		
		CallEvent start = new CallEvent("4894613648979", "447766406373", date.getTime());
		CallEvent end = new CallEvent("4894613648979", "447766406373", date.getTime()+1000);
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = new LineItem(call, callCost);
		assertEquals(l.date(),SimpleDateFormat.getInstance().format( date));
	}

	@Test
	public void testCallee() {
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		
		CallEvent start = new CallEvent("4894613648979", "447766406373", date.getTime());
		CallEvent end = new CallEvent("4894613648979", "447766406373", date.getTime()+1000);
		
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = new LineItem(call, callCost);
		assertEquals(l.callee(),start.getCallee());	
	}

	@Test
	public void testDurationMinutes() {
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		
		int time=1000;
		CallEvent start = new CallEvent("4894613648979", "447766406373", date.getTime());
		CallEvent end = new CallEvent("4894613648979", "447766406373", date.getTime()+time);
		
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = new LineItem(call, callCost);
		assertEquals(l.durationMinutes(),("" + time/1000 / 60 + ":" + String.format("%02d", time/1000 % 60)));
	}

	@Test
	public void testCost() {
		Date date=new Date();
		date.setTime(System.currentTimeMillis());
		
		CallEvent start = new CallEvent("4894613648979", "447766406373", date.getTime());
		CallEvent end = new CallEvent("4894613648979", "447766406373", date.getTime()+1000);
		BigDecimal callCost = new BigDecimal(1000);	
		Call call = new Call(start, end);
		
		LineItem l = new LineItem(call, callCost);
		assertEquals(l.cost(),callCost);
	}

}

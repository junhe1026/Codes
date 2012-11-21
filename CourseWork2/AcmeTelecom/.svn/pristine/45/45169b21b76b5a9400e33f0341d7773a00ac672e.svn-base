package com.acmetelecom.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.acmetelecom.customer.Customer;

import static com.acmetelecom.builders.CustomerBuilder.aCustomer;

public class CustomerBuilderTest {

	@Test
	public void testACustomerWithName() {
		Customer c = aCustomer().named("John").build(); 
		assertEquals(c.getFullName(), "John");
	}
	
	@Test 
	public void testACustomerWithPhoneNumber() {
		Customer c = aCustomer().withPhoneNumber("07427476882").build();
		assertEquals(c.getPhoneNumber(),"07427476882");
	}
	
	@Test
	public void testACustomerAndPricePlan() {
		Customer c = aCustomer().andPricePlan("Save Money.").build();
		assertEquals(c.getPricePlan(),"Save Money.");
	}

}

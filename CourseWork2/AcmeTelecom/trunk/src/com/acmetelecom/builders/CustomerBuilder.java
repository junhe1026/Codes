package com.acmetelecom.builders;

import com.acmetelecom.customer.Customer;

public class CustomerBuilder implements Builder {
	private String customerName;
	private String phoneNumber;
	private String pricePlan; 
	
	public static CustomerBuilder aCustomer() {
		return new CustomerBuilder();
	}
	
	private CustomerBuilder() {
		this.customerName = null;
		this.phoneNumber = null;
		this.pricePlan = null; 
	}
	
	public CustomerBuilder named(String customerName) {
		this.customerName = customerName;
		return this;
	}
	
	public CustomerBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public CustomerBuilder andPricePlan(String pricePlan) {
		this.pricePlan = pricePlan;
		return this;
	}
	
	public Customer build() {
		return new Customer(this.customerName, this.phoneNumber, this.pricePlan);
	}
}

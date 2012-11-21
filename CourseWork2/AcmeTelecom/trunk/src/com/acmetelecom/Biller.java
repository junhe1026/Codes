package com.acmetelecom;

public interface Biller {

	public void callInitiated(String caller, String callee);

	public void callCompleted(String caller, String callee);

	public void createCustomerBills();

}
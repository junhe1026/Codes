package com.acmetelecom;

import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.platform.HtmlPrinter;
import com.acmetelecom.platform.SystemClock;
import com.acmetelecom.rates.OffpeakRateSelector;

public class BillingSystem implements Biller {
	private ConfigurableBillingSystem billingSystem = null;
	
	public BillingSystem() {
		this.billingSystem = new ConfigurableBillingSystem(
				CentralCustomerDatabase.getInstance(),
				CentralTariffDatabase.getInstance(),
				new BillGenerator(HtmlPrinter.getInstance()),
				new OffpeakRateSelector(7, 19),
				new SystemClock());
	} 
	
	@Override
	public void callCompleted(String caller, String callee) {
		this.billingSystem.callCompleted(caller, callee);
	}

	@Override
	public void callInitiated(String caller, String callee) {
		this.billingSystem.callInitiated(caller, callee);
	}

	@Override
	public void createCustomerBills() {
		this.billingSystem.createCustomerBills();
	}
}

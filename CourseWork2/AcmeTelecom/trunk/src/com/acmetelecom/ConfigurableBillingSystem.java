package com.acmetelecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.platform.Clock;
import com.acmetelecom.rates.RateDescriptor;
import com.acmetelecom.rates.RateSelector;

public class ConfigurableBillingSystem implements Biller {

    private List<CallEvent> callLog = null;
    
    private CustomerDatabase customerDatabase = null;
    private TariffLibrary tariffLibrary = null;
    Generator generator = null;
    Clock clock = null;
    RateSelector selector = null;
    
    /*
     * Convenience constructor
     */
    public ConfigurableBillingSystem(CustomerDatabase customerDatabase, TariffLibrary tariffLibrary, Generator generator, RateSelector selector, Clock clock) {
    	this.callLog = new ArrayList<CallEvent>();
    	
    	// Initialise all the dependencies of the billing system
    	this.customerDatabase = customerDatabase; 
    	this.tariffLibrary = tariffLibrary;
    	
    	this.generator = generator;
    	this.clock = clock;
    	
    	this.selector = selector;
    }
    
    /* (non-Javadoc)
	 * @see com.acmetelecom.Biller#callInitiated(java.lang.String, java.lang.String)
	 */
    public void callInitiated(String caller, String callee) {
        callLog.add(new CallStart(caller, callee, this.clock));
    }

    /* (non-Javadoc)
	 * @see com.acmetelecom.Biller#callCompleted(java.lang.String, java.lang.String)
	 */
    public void callCompleted(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee, this.clock));
    }

    /* (non-Javadoc)
	 * @see com.acmetelecom.Biller#createCustomerBills()
	 */
    public void createCustomerBills() {
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            createBillFor(customer);
        }
        callLog.clear();
    }

    private void createBillFor(Customer customer) {
        List<CallEvent> customerEvents = new ArrayList<CallEvent>();
        for (CallEvent callEvent : callLog) {
            if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
                customerEvents.add(callEvent);
            }
        }

        List<Call> calls = new ArrayList<Call>();

        CallEvent start = null;
        for (CallEvent event : customerEvents) {
            if (event instanceof CallStart) {
                start = event;
            }
            if (event instanceof CallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }

        BigDecimal totalBill = new BigDecimal(0);
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {

            Tariff tariff = this.tariffLibrary.tarriffFor(customer);

            BigDecimal cost = BigDecimal.ZERO;

            // Using the rateSelector, determine how much to charge for the call            
            Collection<RateDescriptor> rates = selector.determineRates(tariff, call);
            if(rates != null) {
            	for(RateDescriptor descriptor : rates) {
            		cost = cost.add(new BigDecimal(descriptor.getSeconds()).multiply(descriptor.getRate())); 
            	}
            }
            
            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            totalBill = totalBill.add(callCost);
            items.add(new LineItem(call, callCost));
        }

        this.generator.send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }
}

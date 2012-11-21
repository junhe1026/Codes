package com.acmetelecom.fakes;

import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Customer;

import java.util.ArrayList;

/**
 * Fake customer database that allows adding specific customers. This 
 * fake will be used by acceptance tests to setup test fixtures. In
 * these tests we need the database to return specific customers. 
 */
public class FakeCustomerDatabase implements CustomerDatabase {
    private ArrayList<Customer> customers;

    public FakeCustomerDatabase()
    {
        customers = new ArrayList<Customer>();
    }
    
	public java.util.List<Customer> getCustomers()
	{
		 return customers;
	}

	/**
	 * Add a customer to the database
	 * @param newCustomer New customer
	 */
    public void addCustomer(Customer newCustomer)
    {
        customers.add(newCustomer);
    }

    /**
     * Clear the customer database
     */
    public void clear()
    {
        customers.clear();
    }
}

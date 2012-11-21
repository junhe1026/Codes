package com.acmetelecom.test;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.fakes.FakeCustomerDatabase;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;

import static com.acmetelecom.builders.CustomerBuilder.aCustomer;

public class FakeCustomerDataBaseTest {
	// Customer objects for the test. We use builders to construct instances. 
    final Customer c1 = aCustomer().
						named("John").
						withPhoneNumber("447766406373").build();

	final Customer c2 = aCustomer().
						named("Susan").
						withPhoneNumber("447920434499").build();

    @Test
    public void testFakeDatabase() {
        //Must use concrete type due to addCustomer not existing in interface
        FakeCustomerDatabase database = new FakeCustomerDatabase();
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(c1);
        customers.add(c2);
        database.addCustomer(c1);
        database.addCustomer(c2);

        List<Customer> reply =  database.getCustomers();
        for(int i=0; i< customers.size()  ; i++)
            assertEquals(customers.get(i),reply.get(i));
    }
}

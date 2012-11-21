package com.acmetelecom.test;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.fakes.FakeTariffLibrary;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import static com.acmetelecom.builders.CustomerBuilder.aCustomer;

/**
 * Simple test for the fake tariff database.
 */
public class FakeTariffLibraryTest {
    // Customer object for the test
	final Customer c1 = aCustomer().
						named("John").
						withPhoneNumber("447766406373").
                        andPricePlan("Business").build();

    final FakeTariffLibrary tariffLib = 
    		new FakeTariffLibrary(
    					new BigDecimal(3), 
    					new BigDecimal(5), 
    					BigDecimal.ZERO,
    					BigDecimal.ZERO,
    					BigDecimal.ZERO,
    					BigDecimal.ZERO);

    @Test
    public void testTariffLibraryGetsCustomerTariff()
    {
        BigDecimal peak = tariffLib.tarriffFor(c1).peakRate();
        BigDecimal off =  tariffLib.tarriffFor(c1).offPeakRate();

        assertTrue(peak.equals(new BigDecimal(5)));
        assertTrue(off.equals(new BigDecimal(3)));
    }
}

package com.acmetelecom.fakes;

import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.customer.Customer;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.runner.RunWith;
import org.jmock.lib.legacy.ClassImposteriser;
import jdave.junit4.JDaveRunner;
import static org.junit.Assert.*;

import java.math.BigDecimal;

/**
 * Unfortunately, Tariff is an enumerated final type. There is no interface that
 * we can implement and return concrete classes from a fake tariff library. We wanted 
 * to define our acceptance tests with variable amounts for tariffs, to ensure
 * that the algorithms calculated rates and totals accurately even if tariffs were
 * to change in the future. We decided to use a class imposteriser from JMock to
 * allow us to fake/mock a concrete class/enum such as Tariff.  
 */
@RunWith(JDaveRunner.class)
public class FakeTariffLibrary implements TariffLibrary
{
    public final JUnitRuleMockery context = new JUnitRuleMockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    static Tariff bus;
    static Tariff lei;
    static Tariff std;

    /**
     * The constructor allows to define specific rates for each tariff plan.
     * @param boff Business off-peak rate
     * @param bon Business on-peak rate
     * @param loff Leisure off-peak rate
     * @param lon Leisure on-peak rate
     * @param soff Standard off-peak rate
     * @param son Standard on-epak rate
     */
    public FakeTariffLibrary(	final BigDecimal boff, 
    							final BigDecimal bon, 
    							final BigDecimal loff,
    							final BigDecimal lon, 
    							final BigDecimal soff, 
    							final BigDecimal son) {
		bus = context.mock(Tariff.class,"business");
		lei = context.mock(Tariff.class, "leisure");
		std = context.mock(Tariff.class, "standard");

		context.checking(new Expectations() {{
            allowing(bus).offPeakRate(); will(returnValue(boff) );
            allowing(bus).peakRate(); will(returnValue(bon) );
            allowing(lei).offPeakRate(); will(returnValue(loff) );
            allowing(lei).peakRate(); will(returnValue(lon) );
            allowing(std).offPeakRate(); will(returnValue(soff) );
            allowing(std).peakRate(); will(returnValue(son) );

        }});
        
		assertTrue(true);
    }

    /**
     * Return a fake tariff for the customer, depending on his/her price plan.
     */
    public Tariff tarriffFor(Customer customer) {
        String plan = customer.getPricePlan();
        if (plan.equals("Business")) return bus;
        if (plan.equals("Leisure")) return lei;
        if (plan.equals("Standard")) return std;
        
        return null; //TODO throw exception
    }
}

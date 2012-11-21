package com.acmetelecom.test;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.acmetelecom.BillingSystem;

public class BillingSystemTest {
	public BillingSystemTest() {}
	
	@Test
	public void testCreateCustomerBills() throws InterruptedException {
//        System.out.println("Running...");
//        BillingSystem billingSystem = new BillingSystem();
//        billingSystem.callInitiated("447722113434", "447766814143");
//        sleepSeconds(20);
//        billingSystem.callCompleted("447722113434", "447766814143");
//        billingSystem.callInitiated("447722113434", "447711111111");
//        sleepSeconds(30);
//        billingSystem.callCompleted("447722113434", "447711111111");
//        billingSystem.callInitiated("447777765432", "447711111111");
//        sleepSeconds(60);
//        billingSystem.callCompleted("447777765432", "447711111111");
//        billingSystem.createCustomerBills();
//
//		assertTrue(true);
	}

    private static void sleepSeconds(int n) throws InterruptedException {
        Thread.sleep(n * 1000);
    }	
}

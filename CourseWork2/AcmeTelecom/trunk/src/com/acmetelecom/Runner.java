package com.acmetelecom;

/**
 * Created by IntelliJ IDEA.
 * User: antonio
 * Date: 23/11/2011
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public class Runner {
    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        BillingSystem billingSystem = new BillingSystem();
        billingSystem.callInitiated("447722113434", "447766814143");
        sleepSeconds(20);
        billingSystem.callCompleted("447722113434", "447766814143");
        billingSystem.callInitiated("447722113434", "447711111111");
        sleepSeconds(30);
        billingSystem.callCompleted("447722113434", "447711111111");
        billingSystem.callInitiated("447777765432", "447711111111");
        sleepSeconds(60);
        billingSystem.callCompleted("447777765432", "447711111111");
        billingSystem.createCustomerBills();
    }
    private static void sleepSeconds(int n) throws InterruptedException {
        Thread.sleep(n * 1000);
    }
}

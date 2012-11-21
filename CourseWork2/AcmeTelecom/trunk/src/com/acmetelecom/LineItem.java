package com.acmetelecom;

import java.math.BigDecimal;

/*
 * This class was originally defined as a static inner class in ConfigurableBillingSystem.
 * The problem with this is that LineItem was also used outside this class, particularly
 * in BillGenerator. In the end, this was preventing us from running unit tests on 
 * BillGenerator. We made it a public class, not an inner class anymore.
 */
public class LineItem {
    private Call call;
    private BigDecimal callCost;

    public LineItem(Call call, BigDecimal callCost) {
        this.call = call;
        this.callCost = callCost;
    }

    public Call getCall(){
    	return call;
    }
    public String date() {
        return call.date();
    }

    public String callee() {
        return call.callee();
    }

    public String durationMinutes() {
        return "" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60);
    }

    public BigDecimal cost() {
        return callCost;
    }
}

package com.acmetelecom.fakes;

import com.acmetelecom.platform.Printer;

import java.io.PrintStream;

/**
 * Fake printer that produces output to a specific print stream
 * as tab-separated values. This facilitates the parsing of output
 * values to sense test results in acceptance test fixtures.  
 */
public class FakePrinter implements Printer {
    private PrintStream out = null;

    /**
     * Convenience constructor
     * @param out Output stream for the fake printer object
     */
	public FakePrinter(PrintStream out) {
		this.out = out;
	}

	/**
	 * Print heading
	 */
	public void printHeading(String name, String phoneNumber, String pricePlan) {
        out.print(name + "/" + phoneNumber + " - " + pricePlan + "\n");
    }

	/**
	 * Print bill item (call)
	 */
    public void printItem(String time, String callee, String duration, String cost)
    {
        cost = cost.replace(".", ",");
        String rtime = time.split(" ")[1];
        if(rtime.length() == 0) rtime = "0" + rtime;

        out.print(rtime + "\t" + callee + "\t" + duration + "\t" +  cost +"\n");
    }

	/**
	 * Print bill footer with total amount for the bill
	 */
    public void printTotal(String total) {
    	total = total.replace(".", ",");
        out.print("Total:" + "\t" + total + "\n");
    }
}


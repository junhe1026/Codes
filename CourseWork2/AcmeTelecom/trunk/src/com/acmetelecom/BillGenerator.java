package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.platform.Printer;

public class BillGenerator implements Generator {
	private Printer printer;
	
	public BillGenerator(Printer printer) {
		this.printer = printer;
	}
	
    /* (non-Javadoc)
	 * @see com.acmetelecom.Generator#send(com.acmetelecom.customer.Customer, java.util.List, java.lang.String)
	 */
	@Override
	public void send(Customer customer, Iterable<LineItem> calls, String totalBill) {
        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());

        for (LineItem call : calls) {
            printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        
        printer.printTotal(totalBill);
    }
}

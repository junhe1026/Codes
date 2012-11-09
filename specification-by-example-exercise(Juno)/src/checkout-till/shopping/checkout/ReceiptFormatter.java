package shopping.checkout;

import java.math.BigDecimal;
import java.util.Vector;

public class ReceiptFormatter {
	private final Printer printer;

	public ReceiptFormatter(Printer printer) {
		this.printer = printer;
	}
	
	public void printReceiptLine(Product product, int count, BigDecimal lineTotal) {
		printer.print(count + " " + product.name() + " @ "
				+ product.unitPrice() + " each = " + lineTotal + "\n");
	}
	
	public void printTotalLine(BigDecimal total) {
		printer.print("Total = " + total + "\n");
	}

    public void printDiscountLine(BigDecimal discount){
        printer.print("Discount = -"+discount+"\n");
    }

    public void printFreeProductsLines(Vector<Product> products){
        for (int i = 0; i < products.size(); i++){
            printer.print("--for "+products.get(i).name()+" "+products.get(i).unitPrice()+"\n");
        }
    }

    public void printSubTotalLine(BigDecimal subtotal){
        printer.print("SubTotal = " + subtotal + "\n");
    }
	
	public void endOfReceipt() {
		printer.feed();
	}
}
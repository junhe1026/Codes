package shopping.checkout;

import javax.swing.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * The central logic of the checkout till.
 */
public class Checkout implements BarcodeScanListener {
	private final ProductRange productRange;
    private final TPOTProductRange tpotProductRange;
	private final ReceiptFormatter printer;
	private final CustomerInformationDisplay display;
	
	private final LinkedHashMap<Product, Integer> scannedProducts = new LinkedHashMap<Product, Integer>();

    private TPOPEngine engine = new TPOPEngine();
	
	public Checkout(ProductRange productRange, TPOTProductRange tpotProductRange,LEDDisplay display, Beeper beeper, Printer printer) {
		this.productRange = productRange;
        this.tpotProductRange = tpotProductRange;
		this.printer = new ReceiptFormatter(printer);
		this.display = new CustomerInformationDisplay(display, beeper);
	}
	
	public void reset() {
		scannedProducts.clear();
        engine.reset();
	}

    public BigDecimal savingTotal(){
        return engine.calDiscount();
    }
	
	public void barcodeScanned(String barcode) {
		Product product;
		try {
			product = productRange.productWithBarcode(barcode);
			scannedProducts.put( product, unitsScanned(product) + 1);
			display.displayRunningTotal(runningTotal());
		} catch (ProductNotFoundException e) {
			display.reportError("unknown product");
		}
        engine.setScannedProductsANDTPOPProductRange(scannedProducts,tpotProductRange);
	}
	
	public BigDecimal runningTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for (Product product : scannedProducts.keySet()) {
			int count = unitsScanned(product);
			BigDecimal lineTotal = product.priceOf(count);
			
			total = total.add(lineTotal);
		}
        return total;
	}
	
	private int unitsScanned(Product product) {
		if (scannedProducts.containsKey(product)) {
			return scannedProducts.get(product);
		} else {
			return 0;
		}
	}

	public void paymentAccepted() {
		BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
		for (Product product : scannedProducts.keySet()) {
			int count = unitsScanned(product);
			BigDecimal lineTotal = product.priceOf(count);
			
			total = total.add(lineTotal);
			
			printer.printReceiptLine(product, count, lineTotal);
		}
		//discount = getDiscount();
        if (discount.equals(BigDecimal.ZERO)){
            printer.printTotalLine(total);
            printer.endOfReceipt();
        }
        else{
            printer.printSubTotalLine(total);
            printer.printDiscountLine(discount);
          //  printer.printFreeProductsLines(freeProducts);
            printer.printTotalLine(total.subtract(discount));
            printer.endOfReceipt();
        }

	}


}

package shopping.checkout;

import javax.swing.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
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
        private final LinkedHashMap<Product, String> scannedTPOTProducts = new LinkedHashMap<Product, String>();
        private final Vector<Product> freeProducts = new Vector<Product>();
	
	public Checkout(ProductRange productRange, TPOTProductRange tpotProductRange,LEDDisplay display, Beeper beeper, Printer printer) {
		this.productRange = productRange;
        this.tpotProductRange = tpotProductRange;
		this.printer = new ReceiptFormatter(printer);
		this.display = new CustomerInformationDisplay(display, beeper);
	}
	
	public void reset() {
		scannedProducts.clear();
        scannedTPOTProducts.clear();
        freeProducts.clear();
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
	}
	
	private BigDecimal runningTotal() {
		BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
		
		for (Product product : scannedProducts.keySet()) {
			int count = unitsScanned(product);
			BigDecimal lineTotal = product.priceOf(count);
			
			total = total.add(lineTotal);
		}

		discount = this.getDiscount();
		return total.subtract(discount);
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
		discount = getDiscount();
        if (discount.equals(BigDecimal.ZERO)){
            printer.printTotalLine(total);
            printer.endOfReceipt();
        }
        else{
            printer.printSubTotalLine(total);
            printer.printDiscountLine(discount);
            printer.printFreeProductsLines(freeProducts);
            printer.printTotalLine(total.subtract(discount));
            printer.endOfReceipt();
        }

	}

    public BigDecimal getDiscount(){
        freeProducts.clear();
        BigDecimal discount = BigDecimal.ZERO;
        checkTPOT();
        discount = calDiscount();
        return discount;

    }

    public void checkTPOT(){
        for (Product product : scannedProducts.keySet()){
            if (tpotProductRange.isTPOTProduct(product.name())){
               scannedTPOTProducts.put(product, product.group());
            }
        }
    }

    public BigDecimal calDiscount(){
        BigDecimal accumulation = BigDecimal.ZERO;
        LinkedHashMap<String,Vector<Product>> groupedTPOTProducts = new LinkedHashMap<String, Vector<Product>>();
        for (Map.Entry<Product, Integer> productEntry : scannedProducts.entrySet()){
            Product product = productEntry.getKey();
            int num = productEntry.getValue();
            if (product.group() != null){

            if (groupedTPOTProducts.containsKey(product.group())){
                for (int i=0;i<num;i++){
                    groupedTPOTProducts.get(product.group()).add(product);
                }
            }
            else{
                Vector<Product> v = new Vector<Product>();
                for (int i=0;i<num;i++){
                    v.add(product);
                }
                groupedTPOTProducts.put(product.group(), v);
            }
            }
        }
        for (Vector<Product> sameGroupTPOTProducts : groupedTPOTProducts.values()){
            getFreeProduct(getOrderedProducts(sameGroupTPOTProducts));
        }

        for (Product product : freeProducts ){
            accumulation = accumulation.add(product.unitPrice());
        }
        return accumulation;
    }

    public Product[] getOrderedProducts(Vector<Product> products){
        Product[] temp = new Product[products.size()];
        int count =0;
        while (products.size() != 0){

            BigDecimal tP=products.get(0).unitPrice();
            int index=0;
            for(int i=0;i<products.size();i++){
                if(products.get(i).unitPrice().signum()<tP.signum()){
                    tP = products.get(i).unitPrice();
                    index=i;
                }
            }
            temp[count]=products.get(index);
            products.remove(index);
            count++;
        }
        return temp;
    }

    public void getFreeProduct(Product[] orderedProducts){
        int num = 0 ;
        if (orderedProducts.length >= 3){
            num = (orderedProducts.length / 3);
            for (int i = 0; i < num; i++){
                freeProducts.add(orderedProducts[i]);
            }
        }

    }
}

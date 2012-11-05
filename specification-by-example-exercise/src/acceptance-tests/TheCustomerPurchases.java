import shopping.checkout.Product;
import fit.ColumnFixture;


public class TheCustomerPurchases extends ColumnFixture {
	public String Product; 
	public String Barcode;

	@Override
	public void reset() {
		Product = null;
		Barcode = null;
		SystemUnderTest.beeper.clearBeepCount();
	}
	
	@Override
	public void execute() {
		if (Product != null) {
			Product product = SystemUnderTest.productRange.productNamed(Product);
			Barcode = product.barcode();
		}
		
		SystemUnderTest.till.barcodeScanned(Barcode);
	}
	
	public String Displayed() {
		return SystemUnderTest.display.displayedText();
	}
	
	public boolean Beeped() {
		return SystemUnderTest.beeper.hasBeeped();
	}
}

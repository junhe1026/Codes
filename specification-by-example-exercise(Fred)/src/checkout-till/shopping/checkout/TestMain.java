package shopping.checkout;

import shopping.checkout.fakes.FakeBeeper;
import shopping.checkout.fakes.FakeLEDDisplay;
import shopping.checkout.fakes.FakePrinter;
import shopping.checkout.fakes.FakeProductRange;
import sun.net.idn.StringPrep;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: yw6312
 * Date: 09/11/12
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class TestMain {
    public static final FakeProductRange productRange = new FakeProductRange();
    public static final TPOTProductRange tpotProductRange = new TPOTProductRange(productRange);
    public static final FakeLEDDisplay display = new FakeLEDDisplay();
    public static final FakePrinter printer = new FakePrinter();
    public static final FakeBeeper beeper = new FakeBeeper();


    public static void main(String[] args){

        // products
        Product milk = new Product("Milk","1",new BigDecimal(10));
        Product bread = new Product("Bread","2",new BigDecimal(100));
        Product cheese = new Product("Cheese","3",new BigDecimal(1000));
        Product wine = new Product("Wine","4",new BigDecimal(10000));


        // add to range
        productRange.addProduct(milk);
        productRange.addProduct(bread);
        productRange.addProduct(cheese);
        productRange.addProduct(wine);

        // set group
        tpotProductRange.setTPOTProductRange(productRange);
        tpotProductRange.setProductGroup(milk, "1");
        tpotProductRange.setProductGroup(bread,"2");
        tpotProductRange.setProductGroup(cheese,"2");

        // purchase
        Checkout till = new Checkout(productRange, tpotProductRange,display, beeper, printer);
        till.barcodeScanned("1");
        till.barcodeScanned("2");
        till.barcodeScanned("1");
        till.barcodeScanned("3");
        till.barcodeScanned("1");
        till.barcodeScanned("4");

        System.out.println("total---------------------"+till.runningTotal());
        System.out.println("Savings >>>>>>>>>>>>>>>>>>>"+ till.savingTotal());

    }
}
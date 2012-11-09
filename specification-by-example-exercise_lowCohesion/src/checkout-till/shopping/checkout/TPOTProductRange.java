package shopping.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Juno HE
 * Date: 05/11/12
 * Time: 13:49
 */
public class TPOTProductRange implements ProductRange {
    private final Map<String, Product> productsByBarcode = new HashMap<String, Product>();
    private final Map<String, Product> productsByName = new HashMap<String, Product>();
    private ProductRange productRange;

    public TPOTProductRange(ProductRange productRange){
        setTPOTProductRange(productRange);
    }

    public void setTPOTProductRange(ProductRange productRange){
        this.productRange = productRange;
    }

    @Override
    public Product productWithBarcode(String barcode) throws ProductNotFoundException {
        if (productsByBarcode.containsKey(barcode)) {
            return productsByBarcode.get(barcode);
        }
        else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public Product productNamed(String name) {
        return productsByName.get(name);
    }

    public void deleteAll() {
        productsByBarcode.clear();
        productsByName.clear();
    }

    public void addProduct(String name, String group) {
        Product product =  productRange.productNamed(name);
        if (productsByBarcode.containsKey(product.barcode())) {
            throw new IllegalArgumentException("duplicate barcode: "
                    + product.barcode());
        }

        if (productsByName.containsKey(product.name())) {
            throw new IllegalArgumentException("duplicate product name: "
                    + product.name());
        }
        setProductGroup(product,group);
        productsByBarcode.put(product.barcode(), product);
        productsByName.put(product.name(), product);
    }


    public void setProductGroup(Product product,String group){
        product.setGroup(group);
    }

    public boolean isTPOTProduct(String name){
        return productsByName.containsKey(name);
    }
}

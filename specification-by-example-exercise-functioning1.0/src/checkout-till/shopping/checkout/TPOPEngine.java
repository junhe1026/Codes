package shopping.checkout;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: yw6312
 * Date: 09/11/12
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public class TPOPEngine {

    private   LinkedHashMap<Product, Integer> scannedProducts;
    private TPOTProductRange tpotProductRange;

    private final LinkedHashMap<Product, String> scannedTPOTProducts = new LinkedHashMap<Product, String>();
    private final Vector<Product> freeProducts = new Vector<Product>();

    public void setScannedProductsANDTPOPProductRange( LinkedHashMap<Product, Integer> scannedProducts,TPOTProductRange tpotProductRange){
        this.scannedProducts=scannedProducts;
        this.tpotProductRange=tpotProductRange;
    }

    protected void reset(){
        scannedTPOTProducts.clear();
        freeProducts.clear();
    }
    public BigDecimal getDiscount(){
        BigDecimal discount = BigDecimal.ZERO;
        checkTPOT();
        discount = calDiscount();
        System.out.println("--------------------------> "+discount);
        return discount;

    }

    public void checkTPOT(){
        for (Product product : scannedProducts.keySet()){
            if (tpotProductRange.isTPOTProduct(product.name())){
                scannedTPOTProducts.put(product, product.group());
            }
        }
    }

    public Vector<Product>  getFreeProducts(){
        return freeProducts;
    }


    public BigDecimal calDiscount(){
        BigDecimal accumulation = BigDecimal.ZERO;
        LinkedHashMap<String,Vector<Product>> groupedTPOTProducts = new LinkedHashMap<String, Vector<Product>>();

        for (Map.Entry<Product,Integer> productEntry : scannedProducts.entrySet()){
            Product product = productEntry.getKey();
            int num = productEntry.getValue();
            if (groupedTPOTProducts.containsKey(product.group())){
                for (int i=0; i<num;i++){
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
        for (Vector<Product> sameGroupTPOTProducts : groupedTPOTProducts.values()){
            System.out.println(sameGroupTPOTProducts.size());
            getFreeProduct(getOrderedProducts(sameGroupTPOTProducts));
            System.out.println(freeProducts.size());
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

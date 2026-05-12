import java.util.ArrayList;
import java.util.Map;

public class StoreController {
    
    private final OnlineStore store;
    private final OutputPort output;
    
    public StoreController(OnlineStore store, OutputPort output) {
        this.store = store;
        this.output = output;
    }
    
    public void displayStoreState() {
        output.println("\nStore state:\n");
        output.printStore(store);
    }
    
    public void groupProductsByType() {
        output.println("\nGrouped products:\n");
        Map<String, ArrayList<Product>> groupedProducts = store.groupProductsByType();
        for (Map.Entry<String, ArrayList<Product>> entry : groupedProducts.entrySet()) {
            output.println(entry.getKey() + ":");
            for (Product product : entry.getValue()) {
                output.println(product);
            }
        }
    }
    
    public void countProducts() {
        output.println("\nNumber of products:\n " + store.countProducts() + "\n");
    }
    
    public void removeSoldOutProducts() {
        output.println("\nRemoving sold out products:\n");
        store.removeSoldOutProducts();
    }
    
    public void removeProduct(int index) {
        output.println("\nRemoving product:\n");
        store.removeProductIdx(index);
    }
    
    public void removeProductType(String productType) {
        output.println("\nRemoving product type:\n");
        store.removeProductType(productType);
    }
    
    public void increaseProductQuantity(int index, int quantity) {
        output.println("\nIncreasing product quantity:\n");
        store.increaseProductQuantity(index, quantity);
    }
    
    public void decreaseProductQuantity(int index, int quantity) {
        output.println("\nDecreasing product quantity:\n");
        store.decreaseProductQuantity(index, quantity);
    }
    
    public void addProduct(Product product) {
        output.println("\nAdding product:\n");
        store.addProduct(product);
    }
    
    public OnlineStore getStore() {
        return store;
    }
}
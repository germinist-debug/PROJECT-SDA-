import java.util.ArrayList;
import java.util.Map;

public class StoreDisplayService {
    
    private ConsoleOutputWriter output;
    
    public StoreDisplayService(ConsoleOutputWriter output) {
        this.output = output;
    }
    
    public void displayStoreState(OnlineStore store) {
        output.printLine("\n=== STORE STATE ===");
        output.printStore(store);
    }
    
    public void displayGroupedProducts(OnlineStore store) {
        output.printLine("\n=== PRODUCTS GROUPED BY TYPE ===");
        Map<String, ArrayList<Product>> grouped = store.groupProductsByType();
        
        for(Map.Entry<String, ArrayList<Product>> entry : grouped.entrySet()) {
            output.printLine("\n" + entry.getKey() + ":");
            for(Product product : entry.getValue()) {
                output.printLine("  - " + product.getName() + " | Size: " + product.getSize() + " | Price: $" + product.getPrice());
            }
        }
    }
    
    public void displayProductCount(OnlineStore store) {
        Map<String, Integer> counts = store.countProducts();
        output.printLine("\n=== PRODUCT COUNTS ===");
        int total = 0;
        for(Map.Entry<String, Integer> entry : counts.entrySet()) {
            output.printLine(entry.getKey() + ": " + entry.getValue() + " products");
            total += entry.getValue();
        }
        output.printLine("Total products: " + total);
    }
}
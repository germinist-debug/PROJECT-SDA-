import java.util.ArrayList;
import java.util.Objects;

public class ProductService {
    
    private OnlineStore store;
    private ConsoleInputReader input;
    private ConsoleOutputWriter output;
    
    public ProductService(OnlineStore store, ConsoleInputReader input, ConsoleOutputWriter output) {
        this.store = store;
        this.input = input;
        this.output = output;
    }
    
    public void removeSoldOutProducts() {
        int before = store.getProductList().length;
        store.removeSoldOutProducts();
        int after = store.getProductList().length;
        output.printLine("Removed " + (before - after) + " sold out products");
    }
    
    public void removeProduct() {
        displayProductsWithNumbers();
        output.printLine("Enter product number to remove: ");
        int index = input.readInt() - 1;
        
        if(index >= 0 && index < store.getProductList().length) {
            Product removed = store.getProductList()[index];
            store.removeProductIdx(index);
            output.printLine("Removed: " + removed.getName());
        } else {
            output.printLine("Invalid product number");
        }
    }
    
    public void removeProductType() {
        output.printLine("Enter product type to remove (e.g., JEANS, TSHIRT): ");
        String type = input.readLine().toUpperCase();
        
        ArrayList<String> existingTypes = store.getProductTypes();
        if(!existingTypes.contains(type)) {
            output.printLine("Product type not found");
            return;
        }
        
        store.removeProductType(type);
        output.printLine("Removed all products of type: " + type);
    }
    
    public void increaseProductQuantity() {
        displayProductsWithNumbers();
        output.printLine("Enter product number: ");
        int index = input.readInt() - 1;
        
        if(index >= 0 && index < store.getProductList().length) {
            output.printLine("Enter quantity to add: ");
            int quantity = input.readInt();
            store.increaseProductQuantity(index, quantity);
            output.printLine("Added " + quantity + " units");
        } else {
            output.printLine("Invalid product number");
        }
    }
    
    public void decreaseProductQuantity() {
        displayProductsWithNumbers();
        output.printLine("Enter product number: ");
        int index = input.readInt() - 1;
        
        if(index >= 0 && index < store.getProductList().length) {
            output.printLine("Enter quantity to remove: ");
            int quantity = input.readInt();
            store.decreaseProductQuantity(index, quantity);
            output.printLine("Removed " + quantity + " units");
        } else {
            output.printLine("Invalid product number");
        }
    }
    
    public void addProduct() {
        output.printLine("\n=== ADD NEW PRODUCT ===");
        
        output.printLine("Enter product name (JEANS, TSHIRT, etc.): ");
        String name = input.readLine().toUpperCase();
        
        String size = input.readSize();
        String color = input.readColor();
        int price = input.readPrice();
        int quantity = input.readQuantity();
        
        // Determine if bottom wear or top wear
        Product product;
        if(isBottomWear(name)) {
            product = new BottomWear(name, size, color, quantity, price);
        } else {
            product = new TopWear(name, size, color, quantity, price);
        }
        
        store.addProduct(product);
        output.printLine("Product added successfully!");
    }
    
    private void displayProductsWithNumbers() {
        output.printLine("\n=== CURRENT PRODUCTS ===");
        Product[] products = store.getProductList();
        for(int i = 0; i < products.length; i++) {
            output.printLine((i+1) + ". " + products[i].getName() + " | Size: " + products[i].getSize() + 
                           " | Stock: " + products[i].getQuantity() + " | Price: $" + products[i].getPrice());
        }
    }
    
    private boolean isBottomWear(String name) {
        String[] bottomTypes = {"JEANS", "SHORTS", "PANTS", "SKIRT"};
        for(String type : bottomTypes) {
            if(type.equals(name)) return true;
        }
        return false;
    }
}
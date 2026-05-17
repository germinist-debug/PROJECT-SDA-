import java.util.*;

public class OnlineStore {
    
    private ArrayList<Product> productList = new ArrayList<>();
    private Owner owner;
    private boolean initialized = false;
    
    public OnlineStore(Owner owner) {
        this.owner = owner;
        this.initialized = true;
    }
    
    public OnlineStore(Owner owner, ArrayList<Product> productList) {
        this.owner = owner;
        this.productList = productList;
        this.initialized = true;
    }
    
    // Product management
    public void addProduct(Product product) {
        for(Product p : productList) {
            if(p.equals(product)) {
                p.increaseQuantity(product.getQuantity());
                p.setPrice(product.getPrice());
                return;
            }
        }
        productList.add(product);
        sortProducts();
    }
    
    public void removeProductIdx(int idx) {
        if(idx >= 0 && idx < productList.size()) {
            productList.remove(idx);
        }
    }
    
    public void removeProductType(String name) {
        Iterator<Product> iterator = productList.iterator();
        while(iterator.hasNext()) {
            Product p = iterator.next();
            if(p.getName().equals(name)) {
                iterator.remove();
            }
        }
    }
    
    public void removeSoldOutProducts() {
        Iterator<Product> iterator = productList.iterator();
        while(iterator.hasNext()) {
            Product p = iterator.next();
            if(p.getQuantity() == 0) {
                iterator.remove();
            }
        }
    }
    
    public void increaseProductQuantity(int index, int quantity) {
        if(index >= 0 && index < productList.size()) {
            productList.get(index).increaseQuantity(quantity);
        }
    }
    
    public void decreaseProductQuantity(int index, int quantity) {
        if(index >= 0 && index < productList.size()) {
            productList.get(index).decreaseQuantity(quantity);
        }
    }
    
    // Display helpers
    public Map<String, ArrayList<Product>> groupProductsByType() {
        Map<String, ArrayList<Product>> productMap = new HashMap<>();
        for(Product product : productList) {
            String name = product.getName();
            if(productMap.containsKey(name)) {
                productMap.get(name).add(product);
            } else {
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                productMap.put(name, products);
            }
        }
        return productMap;
    }
    
    public Map<String, Integer> countProducts() {
        Map<String, Integer> productMap = new HashMap<>();
        for(Product product : productList) {
            String name = product.getName();
            productMap.put(name, productMap.getOrDefault(name, 0) + 1);
        }
        return productMap;
    }
    
    public ArrayList<String> getProductTypes() {
        ArrayList<String> types = new ArrayList<>();
        for(Product product : productList) {
            if(!types.contains(product.getName())) {
                types.add(product.getName());
            }
        }
        return types;
    }
    
    // Getters
    public Product[] getProductList() {
        return productList.toArray(new Product[0]);
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    public boolean isInitialized() {
        return initialized;
    }
    
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    
    // Private helpers
    private void sortProducts() {
        productList.sort(Comparator.naturalOrder());
    }
}
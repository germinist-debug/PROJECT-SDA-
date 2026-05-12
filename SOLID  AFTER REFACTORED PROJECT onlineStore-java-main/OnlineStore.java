import java.util.*;

/// OnlineStore class that represents the store. It has a list of products and an owner.
/// It has methods to add, remove, and sort products.
/// It also has methods to group products by type and count products.
/// 
/// ✅ DIP APPLIED: Depends on abstractions (interfaces), not concrete classes
/// ✅ SRP APPLIED: Handles only store logic, not database operations
public class OnlineStore {

    // =========================================================
    // DEPENDENCIES (Injected via constructor - DIP)
    // Depend on ABSTRACTIONS, not concrete implementations
    // =========================================================
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final OwnerRepository ownerRepository;

    // Store data
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Client> clientList = new ArrayList<>();

    // Owner
    private Owner owner;
    private boolean initialized = false;

    // =========================================================
    // CONSTRUCTORS (Dependency Injection)
    // =========================================================
    
    public OnlineStore(Owner owner,
                       ProductRepository productRepository,
                       ClientRepository clientRepository,
                       OrderRepository orderRepository,
                       OwnerRepository ownerRepository) {
        this.owner = owner;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.ownerRepository = ownerRepository;
        
        // Load existing products from database if any
        if (owner != null && productRepository != null) {
            this.productList = productRepository.getAll();
            sortProducts();
        }
    }

    public OnlineStore(ProductRepository productRepository,
                       ClientRepository clientRepository,
                       OrderRepository orderRepository,
                       OwnerRepository ownerRepository) {
        this(null, productRepository, clientRepository, orderRepository, ownerRepository);
    }

    // =========================================================
    // INITIALIZATION METHODS
    // =========================================================
    
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    
    public boolean isInitialized() {
        if (ownerRepository.get() != null) {
            initialized = true;
        }
        return initialized;
    }

    public void initializeStore(Owner owner, String password) {
        if (isInitialized()) return;
        this.owner = owner;
        this.initialized = true;
        ownerRepository.insert(owner, password);
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    // =========================================================
    // PRODUCT MANAGEMENT (Store logic only - no database code)
    // =========================================================
    
    private void sortProducts() {
        productList.sort(Comparator.naturalOrder());
    }
    
    private void sortProductsPrice() {
        productList.sort(Comparator.comparing(Product::getPrice));
    }
    
    public void resetProducts() {
        productList.clear();
        productRepository.deleteAll();
    }
    
    public void removeProductIdx(int idx) {
        if (idx < 0 || idx >= productList.size()) {
            System.out.println("Invalid product index");
            return;
        }
        Product product = productList.get(idx);
        productList.remove(idx);
        productRepository.delete(product);
    }
    
    public void removeProductType(String name) {
        if (productList.isEmpty()) return;
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            if (p.getName().equals(name)) {
                iterator.remove();
                productRepository.delete(p);
            }
        }
    }
    
    public void removeSoldOutProducts() {
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            if (p.getQuantity() == 0) {
                iterator.remove();
                productRepository.delete(p);
            }
        }
    }

    // =========================================================
    // ORDER PLACEMENT
    // =========================================================
    
    public boolean placeOrderIdx(int idx, Client client, int quantity) {
        if (idx < 0 || idx >= productList.size()) {
            System.out.println("Invalid product index");
            return false;
        }
        
        Product product = productList.get(idx);
        
        if (product.getQuantity() < quantity) {
            System.out.println("Not enough products in stock");
            return false;
        }
        
        // Update product quantity in memory
        product.decreaseQuantity(quantity);
        
        // Update in database via repository
        productRepository.updateQuantity(product);
        
        // Update client's "to_pay" amount
        int toPay = clientRepository.getToPay(client.getEmail());
        toPay += product.getPrice() * quantity;
        clientRepository.updateToPay(client.getEmail(), toPay);
        
        // Insert order
        orderRepository.insert(client, product, quantity);
        
        return true;
    }

    // =========================================================
    // ADD PRODUCT (with validation)
    // =========================================================
    
    public void addProduct(Product product) {
        // Validate product attributes
        try {
            product.checkAttributes();
        } catch (InvalidProductAttribute e) {
            switch (e.getMessage()) {
                case "Invalid size" -> System.out.println("Invalid size: " + product.getSize());
                case "Invalid color" -> System.out.println("Invalid color: " + product.getColor());
                case "Invalid price" -> System.out.println("Invalid price: " + product.getPrice());
                case "Invalid quantity" -> System.out.println("Invalid quantity: " + product.getQuantity());
                default -> System.out.println("Invalid product attribute: " + e.getMessage());
            }
            return; // Don't add invalid product
        }

        // Check if product already exists in store
        for (Product p : productList) {
            if (p.equals(product)) {
                // Existing product - just increase quantity and update price
                p.increaseQuantity(product.getQuantity());
                p.setPrice(product.getPrice());
                productRepository.updateQuantity(p);
                productRepository.updatePrice(p);
                sortProducts();
                return;
            }
        }
        
        // New product - add to list and database
        productList.add(product);
        productRepository.insert(product);
        sortProducts();
    }

    // =========================================================
    // CLIENT MANAGEMENT
    // =========================================================
    
    public void addClient(Client client) {
        try {
            client.checkAttributes();
        } catch (InvalidPersonAttribute e) {
            switch (e.getMessage()) {
                case "Invalid name" -> System.out.println("Invalid name: " + client.getName());
                case "Invalid phone number" -> System.out.println("Invalid phone number: " + client.getPhoneNumber());
                case "Invalid email" -> System.out.println("Invalid email: " + client.getEmail());
                default -> System.out.println("Invalid client attribute: " + e.getMessage());
            }
            return;
        }
        clientList.add(client);
        // Note: Inserting into database is handled by Application layer
        // because password is required
    }

    // =========================================================
    // PRODUCT LIST MANAGEMENT
    // =========================================================
    
    public void setProductList(ArrayList<Product> productList) {
        resetProducts();
        for (Product product : productList) {
            this.addProduct(product);
        }
    }

    public void loadProductsFromDatabase() {
        this.productList = productRepository.getAll();
        sortProducts();
    }

    // =========================================================
    // QUERY METHODS
    // =========================================================
    
    public Map<String, ArrayList<Product>> groupProductsByType() {
        Map<String, ArrayList<Product>> productMap = new HashMap<>();
        for (Product product : productList) {
            String type = product.getName();
            if (productMap.containsKey(type)) {
                productMap.get(type).add(product);
            } else {
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                productMap.put(type, products);
            }
        }
        return productMap;
    }

    public Map<String, Integer> countProducts() {
        Map<String, Integer> productMap = new HashMap<>();
        for (Product product : productList) {
            String name = product.getName();
            productMap.put(name, productMap.getOrDefault(name, 0) + 1);
        }
        return productMap;
    }

    public ArrayList<String> getProductTypes() {
        ArrayList<String> productTypes = new ArrayList<>();
        for (Product product : productList) {
            if (!productTypes.contains(product.getName())) {
                productTypes.add(product.getName());
            }
        }
        return productTypes;
    }

    // =========================================================
    // PRODUCT QUANTITY MANAGEMENT
    // =========================================================
    
    public void increaseProductQuantity(int index, int quantity) {
        if (index < 0 || index >= productList.size()) {
            System.out.println("Invalid product index");
            return;
        }
        productList.get(index).increaseQuantity(quantity);
        productRepository.updateQuantity(productList.get(index));
    }

    public void decreaseProductQuantity(int index, int quantity) {
        if (index < 0 || index >= productList.size()) {
            System.out.println("Invalid product index");
            return;
        }
        productList.get(index).decreaseQuantity(quantity);
        productRepository.updateQuantity(productList.get(index));
    }

    // =========================================================
    // GETTERS
    // =========================================================
    
    public Product[] getProductList() {
        return productList.toArray(new Product[0]);
    }

    public ArrayList<Product> getProductListAsList() {
        return new ArrayList<>(productList);
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }

    // =========================================================
    // REPOSITORY GETTERS (for Application layer)
    // =========================================================
    
    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public OwnerRepository getOwnerRepository() {
        return ownerRepository;
    }
}
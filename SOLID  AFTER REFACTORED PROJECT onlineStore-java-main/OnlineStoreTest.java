import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

class OnlineStoreTest {

    private ProductRepository mockProductRepo;
    private ClientRepository mockClientRepo;
    private OrderRepository mockOrderRepo;
    private OwnerRepository mockOwnerRepo;
    private Owner owner;
    
    @BeforeEach
    void setUp() {
        // Create mock repositories (simple in-memory implementations for testing)
        mockProductRepo = new InMemoryProductRepository();
        mockClientRepo = new InMemoryClientRepository();
        mockOrderRepo = new InMemoryOrderRepository();
        mockOwnerRepo = new InMemoryOwnerRepository();
        owner = new Owner("John Doe", "1234567890", "john@example.com");
    }

    @Test
    void testConstructor() {
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new TopWear("SHIRT", "M", "BLUE", 10, 20));

        OnlineStore store1 = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        assertEquals(owner, store1.getOwner());
        assertEquals(0, store1.getProductList().length);

        OnlineStore store2 = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        store2.setProductList(productList);
        assertEquals(owner, store2.getOwner());
        assertEquals(1, store2.getProductList().length);
    }
    
    @Test
    void testAddProduct() {
        OnlineStore store = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        Product product = new TopWear("SHIRT", "M", "BLUE", 10, 20);

        store.addProduct(product);
        assertEquals(1, store.getProductList().length);
        assertEquals(product, store.getProductList()[0]);
    }

    @Test
    void testRemoveProductIdx() {
        Product product = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        
        OnlineStore store = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        store.setProductList(productList);
        assertEquals(1, store.getProductList().length);
        
        store.removeProductIdx(0);
        assertEquals(0, store.getProductList().length);
    }

    @Test
    void testRemoveProductType() {
        Product product1 = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        Product product2 = new BottomWear("JEANS", "L", "BLACK", 5, 30);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        
        OnlineStore store = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        store.setProductList(productList);
        assertEquals(2, store.getProductList().length);
        
        store.removeProductType("SHIRT");
        assertEquals(1, store.getProductList().length);
        assertEquals("JEANS", store.getProductList()[0].getName());
    }

    @Test
    void testSetProductList() {
        Product product1 = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        Product product2 = new BottomWear("JEANS", "L", "BLACK", 5, 30);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        
        OnlineStore store = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        store.setProductList(productList);
        assertEquals(2, store.getProductList().length);
        assertTrue(store.getProductList().length >= 2);
    }
    
    @Test
    void testGroupProductsByType() {
        Product product1 = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        Product product2 = new TopWear("SHIRT", "L", "RED", 5, 25);
        Product product3 = new BottomWear("JEANS", "32", "BLUE", 8, 40);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        
        OnlineStore store = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        store.setProductList(productList);
        
        var grouped = store.groupProductsByType();
        assertTrue(grouped.containsKey("SHIRT"));
        assertTrue(grouped.containsKey("JEANS"));
        assertEquals(2, grouped.get("SHIRT").size());
        assertEquals(1, grouped.get("JEANS").size());
    }
    
    @Test
    void testCountProducts() {
        Product product1 = new TopWear("SHIRT", "M", "BLUE", 10, 20);
        Product product2 = new TopWear("SHIRT", "L", "RED", 5, 25);
        Product product3 = new BottomWear("JEANS", "32", "BLUE", 8, 40);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        
        OnlineStore store = new OnlineStore(owner, mockProductRepo, mockClientRepo, mockOrderRepo, mockOwnerRepo);
        store.setProductList(productList);
        
        var counts = store.countProducts();
        assertEquals(2, counts.get("SHIRT"));
        assertEquals(1, counts.get("JEANS"));
    }
    
    // ========== IN-MEMORY MOCK REPOSITORIES FOR TESTING ==========
    
    private static class InMemoryProductRepository implements ProductRepository {
        private ArrayList<Product> products = new ArrayList<>();
        
        @Override
        public void insert(Product product) { products.add(product); }
        
        @Override
        public void updateQuantity(Product product) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).equals(product)) {
                    products.get(i).increaseQuantity(product.getQuantity());
                    break;
                }
            }
        }
        
        @Override
        public void updatePrice(Product product) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).equals(product)) {
                    products.get(i).setPrice(product.getPrice());
                    break;
                }
            }
        }
        
        @Override
        public void delete(Product product) { products.removeIf(p -> p.equals(product)); }
        
        @Override
        public void deleteAll() { products.clear(); }
        
        @Override
        public ArrayList<Product> getAll() { return new ArrayList<>(products); }
        
        @Override
        public Product findByNameSizeColor(String name, String size, String color) {
            for (Product p : products) {
                if (p.getName().equals(name) && p.getSize().equals(size) && p.getColor().equals(color)) {
                    return p;
                }
            }
            return null;
        }
    }
    
    private static class InMemoryClientRepository implements ClientRepository {
        private ArrayList<Client> clients = new ArrayList<>();
        
        @Override
        public void insert(Client client, String password) { clients.add(client); }
        
        @Override
        public void update(Client client, String password) {
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getEmail().equals(client.getEmail())) {
                    clients.set(i, client);
                    break;
                }
            }
        }
        
        @Override
        public void delete(String email) { clients.removeIf(c -> c.getEmail().equals(email)); }
        
        @Override
        public void deleteAll() { clients.clear(); }
        
        @Override
        public Client findByEmail(String email) {
            return clients.stream().filter(c -> c.getEmail().equals(email)).findFirst().orElse(null);
        }
        
        @Override
        public boolean emailExists(String email) { return findByEmail(email) != null; }
        
        @Override
        public String getPassword(String email) { return "test123"; }
        
        @Override
        public String getName(String email) {
            Client c = findByEmail(email);
            return c != null ? c.getName() : null;
        }
        
        @Override
        public String getPhoneNumber(String email) {
            Client c = findByEmail(email);
            return c != null ? c.getPhoneNumber() : null;
        }
        
        @Override
        public int getToPay(String email) { return 0; }
        
        @Override
        public void updateToPay(String email, int amount) { }
        
        @Override
        public void printAll() { }
    }
    
    private static class InMemoryOrderRepository implements OrderRepository {
        @Override
        public void insert(Client client, Product product, int quantity) { }
        @Override
        public void delete(int orderId) { }
        @Override
        public int getValue(int orderId) { return 0; }
        @Override
        public int getId(Client client, Product product, int quantity) { return 1; }
        @Override
        public ArrayList<Integer> getAllIds() { return new ArrayList<>(); }
        @Override
        public void printAll() { }
        @Override
        public void printByClient(String email) { }
    }
    
    private static class InMemoryOwnerRepository implements OwnerRepository {
        private Owner owner;
        
        @Override
        public void insert(Owner owner, String password) { this.owner = owner; }
        
        @Override
        public void update(Owner owner, String password) { this.owner = owner; }
        
        @Override
        public void delete() { owner = null; }
        
        @Override
        public Owner get() { return owner; }
        
        @Override
        public String getPassword() { return "owner123"; }
        
        @Override
        public String getEmail() { return owner != null ? owner.getEmail() : null; }
        
        @Override
        public String getName() { return owner != null ? owner.getName() : null; }
        
        @Override
        public String getPhoneNumber() { return owner != null ? owner.getPhoneNumber() : null; }
        
        @Override
        public void print() { }
    }
}
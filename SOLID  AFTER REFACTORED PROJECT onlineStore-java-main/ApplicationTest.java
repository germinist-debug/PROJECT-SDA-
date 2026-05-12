import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private InputPort input;
    private OutputPort output;
    private ProductRepository productRepo;
    private ClientRepository clientRepo;
    private OrderRepository orderRepo;
    private OwnerRepository ownerRepo;
    private AuthService authService;
    private FileInputPort fileInput;
    
    @BeforeEach
    public void setUp() {
        // Use real implementations for testing
        input = new ConsoleInput();
        output = new ConsoleOutput();
        fileInput = new ConsoleInput();
        
        // Use in-memory mock repositories for testing (no real database)
        productRepo = new InMemoryProductRepository();
        clientRepo = new InMemoryClientRepository();
        orderRepo = new InMemoryOrderRepository();
        ownerRepo = new InMemoryOwnerRepository();
        authService = new DatabaseAuthService(ownerRepo, clientRepo);
    }

    @Test
    public void testConstructor() {
        Application application = new Application(input, output, productRepo, 
                                                   clientRepo, orderRepo, ownerRepo, 
                                                   authService, fileInput);
        
        assertNotNull(application);
        assertEquals(output, application.getOutput());
    }
    
    @Test
    public void testGetOutput() {
        Application application = new Application(input, output, productRepo, 
                                                   clientRepo, orderRepo, ownerRepo, 
                                                   authService, fileInput);
        
        assertNotNull(application.getOutput());
        assertEquals(output, application.getOutput());
    }
    
    // ========== IN-MEMORY MOCK REPOSITORIES FOR TESTING ==========
    
    private static class InMemoryProductRepository implements ProductRepository {
        private java.util.ArrayList<Product> products = new java.util.ArrayList<>();
        
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
        public java.util.ArrayList<Product> getAll() { return new java.util.ArrayList<>(products); }
        
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
        private java.util.ArrayList<Client> clients = new java.util.ArrayList<>();
        
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
        public java.util.ArrayList<Integer> getAllIds() { return new java.util.ArrayList<>(); }
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
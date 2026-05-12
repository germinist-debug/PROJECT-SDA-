import java.util.ArrayList;

public class AuthController {
    
    private final InputPort input;
    private final OutputPort output;
    private final ProductRepository productRepo;
    private final ClientRepository clientRepo;
    private final OrderRepository orderRepo;
    private final OwnerRepository ownerRepo;
    private final AuthService authService;
    private final FileService fileService;
    private final StoreController storeController;
    private final OwnerMenu ownerMenu;
    private final AdminMenu adminMenu;
    private final ClientMenu clientMenu;
    
    public AuthController(InputPort input, OutputPort output,
                          ProductRepository productRepo,
                          ClientRepository clientRepo,
                          OrderRepository orderRepo,
                          OwnerRepository ownerRepo,
                          AuthService authService,
                          FileService fileService,
                          StoreController storeController,
                          OwnerMenu ownerMenu,
                          AdminMenu adminMenu,
                          ClientMenu clientMenu) {
        this.input = input;
        this.output = output;
        this.productRepo = productRepo;
        this.clientRepo = clientRepo;
        this.orderRepo = orderRepo;
        this.ownerRepo = ownerRepo;
        this.authService = authService;
        this.fileService = fileService;
        this.storeController = storeController;
        this.ownerMenu = ownerMenu;
        this.adminMenu = adminMenu;
        this.clientMenu = clientMenu;
    }
    
    public void runFromDB() {
        DatabaseInitializer.initializeDatabase();
        output.println("Starting store from database");
        
        OnlineStore store = new OnlineStore(productRepo, clientRepo, orderRepo, ownerRepo);
        store.loadProductsFromDatabase();
        
        ArrayList<String> authInfo = input.getAuthenticationInfo();
        String email = authInfo.get(0);
        String password = authInfo.get(1);
        
        String role = authService.checkRole(email, password);
        
        switch (role) {
            case "owner" -> {
                if (!store.isInitialized()) {
                    output.println("Store not initialized\nPlease contact the admin\nExiting...");
                    System.exit(1);
                }
                output.println("Logged in as owner\n\n");
                ownerMenu.show(store);
            }
            case "client" -> {
                if (!store.isInitialized()) {
                    output.println("Store not initialized\nPlease contact the admin\nExiting...");
                    System.exit(1);
                }
                output.println("Logged in as client\n\n");
                clientMenu.show(store, email);
            }
            case "admin" -> {
                output.println("Logged in as admin\n\n");
                adminMenu.show(store);
            }
            case "wrongPassword" -> {
                output.println("Wrong password\n");
                runFromDB();
            }
            case "notFound" -> {
                output.println("Registering new client with email " + email);
                String name = input.readName();
                String phone = input.readPhoneNumber();
                Client client = new Client(name, phone, email);
                clientRepo.insert(client, password);
                output.println("Client registered successfully\n");
                if (!store.isInitialized()) {
                    output.println("Store not initialized\nPlease contact the admin\nExiting...");
                    System.exit(1);
                }
                clientMenu.show(store, email);
            }
            default -> {
                output.println("Invalid credentials");
                System.exit(1);
            }
        }
    }
}
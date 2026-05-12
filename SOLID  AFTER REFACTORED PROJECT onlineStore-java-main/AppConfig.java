public class AppConfig {
    
    public static Application createApplication() {
        // Create abstractions
        ConsoleInput consoleInput = new ConsoleInput();
        InputPort input = consoleInput;
        OutputPort output = new ConsoleOutput();
        FileInputPort fileInput = consoleInput;
        
        // Create repositories
        String dbUrl = DatabaseInitializer.getURL();
        ProductRepository productRepo = new SQLiteProductRepository(dbUrl);
        ClientRepository clientRepo = new SQLiteClientRepository(dbUrl);
        OrderRepository orderRepo = new SQLiteOrderRepository(dbUrl);
        OwnerRepository ownerRepo = new SQLiteOwnerRepository(dbUrl);
        
        // Create auth service
        AuthService authService = new DatabaseAuthService(ownerRepo, clientRepo);
        
        // Create OnlineStore
        OnlineStore store = new OnlineStore(productRepo, clientRepo, orderRepo, ownerRepo);
        store.loadProductsFromDatabase();
        
        // Create controllers and services
        FileService fileService = new FileService(output, fileInput);
        StoreController storeController = new StoreController(store, output);
        AuthController authController = new AuthController(input, output, productRepo, clientRepo, 
                                                            orderRepo, ownerRepo, authService, 
                                                            fileService, storeController, null, null, null);
        
        // Create menus (they need to reference each other, so create with null first then set)
        OwnerMenu ownerMenu = new OwnerMenu(input, output, storeController, orderRepo, authController);
        AdminMenu adminMenu = new AdminMenu(input, output, storeController, productRepo, clientRepo, ownerRepo, authController);
        ClientMenu clientMenu = new ClientMenu(input, output, storeController, clientRepo, orderRepo, authController);
        
        // Update authController with menus
        AuthController fullAuthController = new AuthController(input, output, productRepo, clientRepo, 
                                                                orderRepo, ownerRepo, authService, 
                                                                fileService, storeController, ownerMenu, adminMenu, clientMenu);
        
        // Create application
        Application app = new Application(input, output, fileService, storeController, fullAuthController, fileInput);
        
        return app;
    }
}
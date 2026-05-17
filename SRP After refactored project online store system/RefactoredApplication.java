public class RefactoredApplication {
    
    private ConsoleInputReader input;
    private ConsoleOutputWriter output;
    private StoreInitializer storeInitializer;
    private StoreDisplayService displayService;
    
    public RefactoredApplication() {
        this.input = new ConsoleInputReader();
        this.output = new ConsoleOutputWriter();
        this.storeInitializer = new StoreInitializer(input, output);
        this.displayService = new StoreDisplayService(output);
    }
    
    public void run(String[] args) {
        output.printLine("=== ONLINE STORE MANAGEMENT SYSTEM (SRP Refactored) ===");
        
        OnlineStore store;
        
        if(args.length > 0 && args[0].equals("scratch")) {
            output.printLine("\nCreating store from scratch...");
            store = storeInitializer.createStoreFromScratch();
        } else {
            output.printLine("\nCreating empty store...");
            store = storeInitializer.createEmptyStore();
        }
        
        // Create services with the store
        ProductService productService = new ProductService(store, input, output);
        MenuHandler menuHandler = new MenuHandler(input, output, displayService, productService, store);
        
        // Start the menu
        menuHandler.runOwnerMenu();
    }
    
    public static void main(String[] args) {
        RefactoredApplication app = new RefactoredApplication();
        app.run(args);
    }
}
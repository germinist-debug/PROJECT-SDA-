public class ClientMenu {
    
    private final InputPort input;
    private final OutputPort output;
    private final StoreController storeController;
    private final ClientRepository clientRepo;
    private final OrderRepository orderRepo;
    private final AuthController authController;
    
    public ClientMenu(InputPort input, OutputPort output,
                      StoreController storeController,
                      ClientRepository clientRepo,
                      OrderRepository orderRepo,
                      AuthController authController) {
        this.input = input;
        this.output = output;
        this.storeController = storeController;
        this.clientRepo = clientRepo;
        this.orderRepo = orderRepo;
        this.authController = authController;
    }
    
    public void show(OnlineStore store, String email) {
        Client client = clientRepo.findByEmail(email);
        
        output.println("\tSelect an option:\n");
        output.println("1. Display store state\t\t2. Group products by type");
        output.println("3. Add order\t\t\t\t4. View my orders\t\t");
        output.println("5. View total to pay\t\t6. Logout");
        output.println("7. Exit");
        
        String choice = input.readLine();
        while (choice.isEmpty() || !choice.matches("^[1-7]$")) {
            output.println("Please enter a number between 1 and 7");
            choice = input.readLine();
        }
        
        int option = Integer.parseInt(choice);
        switch (option) {
            case 1 -> storeController.displayStoreState();
            case 2 -> storeController.groupProductsByType();
            case 3 -> {
                int index = input.readProductIndex(store);
                int quantity = input.readQuantity();
                while (!store.placeOrderIdx(index, client, quantity)) {
                    output.println("Not enough stock. Try again.");
                    quantity = input.readQuantity();
                }
                output.println("Order placed successfully!");
            }
            case 4 -> {
                output.println("Your orders:\n");
                orderRepo.printByClient(email);
            }
            case 5 -> output.println("Total to pay: " + clientRepo.getToPay(email));
            case 6 -> {
                output.println("Logging out...\n");
                authController.runFromDB();
                return;
            }
            case 7 -> {
                output.println("Exiting...");
                System.exit(0);
            }
        }
        
        // Save after order placement
        if (output instanceof ConsoleOutput) {
            ((ConsoleOutput) output).saveToFile(store);
        }
        
        show(store, email);
    }
}
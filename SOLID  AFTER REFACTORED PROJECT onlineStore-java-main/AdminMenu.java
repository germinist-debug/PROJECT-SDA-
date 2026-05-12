public class AdminMenu {
    
    private final InputPort input;
    private final OutputPort output;
    private final StoreController storeController;
    private final ProductRepository productRepo;
    private final ClientRepository clientRepo;
    private final OwnerRepository ownerRepo;
    private final AuthController authController;
    
    public AdminMenu(InputPort input, OutputPort output,
                     StoreController storeController,
                     ProductRepository productRepo,
                     ClientRepository clientRepo,
                     OwnerRepository ownerRepo,
                     AuthController authController) {
        this.input = input;
        this.output = output;
        this.storeController = storeController;
        this.productRepo = productRepo;
        this.clientRepo = clientRepo;
        this.ownerRepo = ownerRepo;
        this.authController = authController;
    }
    
    public void show(OnlineStore store) {
        if (!store.isInitialized()) {
            output.println("Please initialize the store using the owner's credentials");
            Owner owner = input.readOwner(false, "", "", "");
            output.println("Now also enter password for the owner: ");
            String password = input.readPassword();
            store.initializeStore(owner, password);
            output.println("Store initialized successfully for owner " + owner.getName());
        }
        
        output.println("\tSelect an option:\n");
        output.println("1. Update owner info\t\t 2. Update client info");
        output.println("3. Add client\t\t\t\t 4. Remove client");
        output.println("5. Remove owner\t\t\t\t 6. Wipe store");
        output.println("7. Remove all clients\t\t 8. Show clients");
        output.println("9. Show owner info\t\t\t10. Add random products");
        output.println("11. Logout\t\t\t\t\t12. Exit");
        
        String choice = input.readLine();
        if (choice.isEmpty() || !choice.matches("^[1-9]$|^1[0-2]$")) {
            output.println("Please enter a number between 1 and 12");
            show(store);
            return;
        }
        
        int option = Integer.parseInt(choice);
        switch (option) {
            case 1 -> {
                output.println("Enter new owner info: ");
                Owner owner = input.readOwner(true, ownerRepo.getEmail(), ownerRepo.getName(), ownerRepo.getPhoneNumber());
                String password = input.readPassword();
                ownerRepo.update(owner, password.isEmpty() ? ownerRepo.getPassword() : password);
                store.setOwner(owner);
            }
            case 2 -> {
                output.println("Enter the email address of the client to update: ");
                String email = input.readEmail();
                while (!clientRepo.emailExists(email)) {
                    output.println("Email does not exist");
                    email = input.readEmail();
                }
                output.println("Enter new client info: ");
                String name = input.readName();
                String phone = input.readPhoneNumber();
                String password = input.readPassword();
                Client client = new Client(
                    name.isEmpty() ? clientRepo.getName(email) : name,
                    phone.isEmpty() ? clientRepo.getPhoneNumber(email) : phone,
                    email
                );
                clientRepo.update(client, password.isEmpty() ? clientRepo.getPassword(email) : password);
            }
            case 3 -> {
                output.println("Enter new client info: ");
                Client client = input.readClient(false, "", "", "");
                String password = input.readPassword();
                clientRepo.insert(client, password);
            }
            case 4 -> {
                output.println("Enter the email address of the client to remove: ");
                String email = input.readEmail();
                while (!clientRepo.emailExists(email)) {
                    output.println("Email does not exist");
                    email = input.readEmail();
                }
                clientRepo.delete(email);
            }
            case 5 -> {
                output.println("Are you sure you want to remove the owner? (y/n)");
                if (input.readLine().equalsIgnoreCase("y")) {
                    ownerRepo.delete();
                    store.setInitialized(false);
                }
            }
            case 6 -> {
                output.println("Are you sure you want to wipe the store? (y/n)");
                if (input.readLine().equalsIgnoreCase("y")) {
                    productRepo.deleteAll();
                    clientRepo.deleteAll();
                    ownerRepo.delete();
                    store.setInitialized(false);
                }
            }
            case 7 -> {
                output.println("Are you sure you want to remove all clients? (y/n)");
                if (input.readLine().equalsIgnoreCase("y")) {
                    clientRepo.deleteAll();
                }
            }
            case 8 -> {
                output.println("Clients:\n");
                clientRepo.printAll();
            }
            case 9 -> {
                output.println("Owner info:\n");
                ownerRepo.print();
            }
            case 10 -> {
                output.println("Adding random products:\n");
                input.populateStore(store);
                output.println("Products added successfully\n");
            }
            case 11 -> {
                output.println("Logging out...\n");
                authController.runFromDB();
                return;
            }
            case 12 -> {
                output.println("Exiting...");
                System.exit(0);
            }
        }
        
        show(store);
    }
}
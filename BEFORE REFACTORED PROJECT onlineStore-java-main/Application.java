import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


// this class is used to test the functionality of the OnlineStore class and its methods
public class Application {
    InputDevice inputDevice;
    OutputDevice outputDevice;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    public void recreateFile(String fileName){

        outputDevice.closeFileOutputStream();
        inputDevice.closeFileInputStream();

        try{
            Files.deleteIfExists(Path.of(fileName));
        }
        catch(NoSuchFileException e){
            e.printStackTrace();
            System.out.println("The file to delete does not exist");
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error deleting file");
        }

        try{
            Files.createFile(Path.of(fileName));
            outputDevice.setFileOutputStream(fileName);
            inputDevice.setFileInputStream(fileName);
        }
        catch(FileAlreadyExistsException e){
            e.printStackTrace();
            System.out.println("The file to create already exists");
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error creating file");
        }
    }

    public void saveStoreToFile(OnlineStore store){
        recreateFile("output.txt");
        outputDevice.storeToFile(store);
    }

    private void runFromScratch(){
        outputDevice.printMessageNl("\nDo you want to generate a dummy owner? (y/n)");
        String input = inputDevice.nextLine();
        while(!input.equals("y") && !input.equals("n") && !input.equals("N") && !input.equals("Y")){
            outputDevice.printMessageNl("Please enter y or n");
            input = inputDevice.nextLine();
        }
        Owner owner = null;
        if(input.equals("Y") || input.equals("y")){
            owner = new Owner("John", "1234567890", "abc@gmail.com");
        }
        else{
            owner = inputDevice.readOwnerFromConsole();
        }
        OnlineStore store = new OnlineStore(owner);
        outputDevice.printMessageNl("\nDo you want to generate random products? (y/n)");
        input = inputDevice.nextLine();
        while(!input.equals("y") && !input.equals("n") && !input.equals("N") && !input.equals("Y")){
            outputDevice.printMessageNl("Please enter y or n");
            input = inputDevice.nextLine();
        }

        if(input.equals("Y") || input.equals("y")){
            outputDevice.printStore(store);
            outputDevice.printMessageNl("\nAdding products:");
            ArrayList<Product> products = inputDevice.getRandomProducts();
            for(Product product:products){ outputDevice.printMessageNl(product);}
            store.setProductList(products);
        }

        saveStoreToFile(store);
        runFileCommandInterface(store);
    }

    private void runFromFile(){

        Owner owner = inputDevice.readOwnerFromFile();
        ArrayList<Product> products = inputDevice.readProductsFromFile();
        OnlineStore store = new OnlineStore(owner, products);
        outputDevice.printStore(store);
        runFileCommandInterface(store);
    }

    private void displayStoreState(OnlineStore store){
        outputDevice.printMessageNl("\nStore state:\n");
        outputDevice.printStore(store);
    }

    private void groupProductsByType(OnlineStore store){
        outputDevice.printMessageNl("\nGrouped products:\n");
        Map<String, ArrayList<Product>> groupedProducts = store.groupProductsByType();
        for (Map.Entry<String, ArrayList<Product>> entry : groupedProducts.entrySet()) {
            outputDevice.printMessageNl(entry.getKey() + ":");
            for (Product product : entry.getValue()) {
                outputDevice.printMessageNl(product);
            }
        }
    }

    private void countProducts(OnlineStore store){
        outputDevice.printMessageNl("\nNumber of products:\n " + store.countProducts() + "\n");
    }

    private void removeSoldOutProducts(OnlineStore store){
        outputDevice.printMessageNl("\nRemoving sold out products:\n");
        store.removeSoldOutProducts();
        saveStoreToFile(store);
    }

    private void removeProduct(OnlineStore store){
        outputDevice.printMessageNl("\nRemoving product:\n");
        int index = inputDevice.getProductIndexFromConsole(store);
        store.removeProductIdx(index);
        saveStoreToFile(store);
    }

    private void removeProductType(OnlineStore store){
        outputDevice.printMessageNl("\nRemoving product type:\n");
        outputDevice.printMessageNl("Enter product type: ");
        String input = inputDevice.nextLine().toUpperCase();
        boolean done = false;
        while(!done){
            if(input.isEmpty()){
                outputDevice.printMessageNl("Please enter a product type");
                input = inputDevice.nextLine().toUpperCase();
            }
            else if(!store.getProductTypes().contains(input)){
                outputDevice.printMessageNl("Product type does not exist");
                input = inputDevice.nextLine().toUpperCase();
            }
            else{
                done = true;
            }
        }
        store.removeProductType(input);
        saveStoreToFile(store);
    }

    private void increaseProductQuantity(OnlineStore store){
        outputDevice.printMessageNl("\nIncreasing product quantity:\n");
        int index = inputDevice.getProductIndexFromConsole(store);
        int quantity = inputDevice.getQuantityFromConsole();
        store.increaseProductQuantity(index, quantity);
        saveStoreToFile(store);
    }

    private void decreaseProductQuantity(OnlineStore store){
        outputDevice.printMessageNl("\nDecreasing product quantity:\n");
        int index = inputDevice.getProductIndexFromConsole(store);
        int quantity = inputDevice.getQuantityFromConsole();
        store.decreaseProductQuantity(index, quantity);
        saveStoreToFile(store);
    }

    private void addProduct(OnlineStore store){
        outputDevice.printMessageNl("\nAdding product:\n");

        ArrayList<String> nameAndType = inputDevice.getNameAndTypeFromConsole();
        String name = nameAndType.get(0);
        String type = nameAndType.get(1);

        String size = inputDevice.getSizeFromConsole();
        String color = inputDevice.getColorFromConsole();
        int price = inputDevice.getPriceFromConsole();
        int quantity = inputDevice.getQuantityFromConsole();

        Product product;
        if(Objects.equals(type, "bottomWear")){
            product = new BottomWear(name, size, color, quantity, price);
        }
        else{
            product = new TopWear(name, size, color, quantity, price);
        }
        store.addProduct(product);
        saveStoreToFile(store);
    }

    private void exit(){
        outputDevice.printMessageNl("Exiting...");
        System.exit(0);
    }

    private void runFileCommandInterface(OnlineStore store){
        outputDevice.printMessageNl("\tSelect an option:\n");
        outputDevice.printMessageNl("1. Display store state\t\t\t2. Group products by type");
        outputDevice.printMessageNl("3. Count products\t\t\t\t4. Remove sold out products");
        outputDevice.printMessageNl("5. Remove product\t\t\t\t6. Remove product type");
        outputDevice.printMessageNl("7. Increase product quantity\t8. Decrease product quantity");
        outputDevice.printMessageNl("9. Add product\t\t\t\t\t10. Exit");

        String input = inputDevice.nextLine();
        int option;
        if (input.isEmpty() || !input.matches("^[1-9]$|^10$")) {
            outputDevice.printMessageNl("Please enter a number between 1 and 10");
            runFileCommandInterface(store);
        }
        option = Integer.parseInt(input);

        switch (option) {
            case 1 -> {
                displayStoreState(store);
                runFileCommandInterface(store);
            }
            case 2 -> {
                groupProductsByType(store);
                runFileCommandInterface(store);
            }
            case 3 -> {
                countProducts(store);
                runFileCommandInterface(store);
            }
            case 4 -> {
                removeSoldOutProducts(store);
                runFileCommandInterface(store);
            }
            case 5 -> {
                removeProduct(store);
                runFileCommandInterface(store);
            }
            case 6 -> {
                removeProductType(store);
                runFileCommandInterface(store);
            }
            case 7 -> {
                increaseProductQuantity(store);
                runFileCommandInterface(store);
            }
            case 8 -> {
                decreaseProductQuantity(store);
                runFileCommandInterface(store);
            }
            case 9 -> {
                addProduct(store);
                runFileCommandInterface(store);
            }
            case 10 -> {
                exit();
            }
        }
    }
    private void runOwnerInterface(OnlineStore store, DatabaseHandler dbHandler) {
        outputDevice.printMessageNl("\tSelect an option:\n");
        outputDevice.printMessageNl("1. Display store state\t\t\t2. Group products by type");
        outputDevice.printMessageNl("3. Count products\t\t\t\t4. Remove sold out products");
        outputDevice.printMessageNl("5. Remove product\t\t\t\t6. Remove product type");
        outputDevice.printMessageNl("7. Increase product quantity\t8. Decrease product quantity");
        outputDevice.printMessageNl("9. Add product\t\t\t\t\t10. View orders");
        outputDevice.printMessageNl("11. Fulfill order\t\t\t\t12. Logout\n13. Exit");

        String input = inputDevice.nextLine();
        int option;
        if (input.isEmpty() || !input.matches("^[1-9]$|^1[0-3]$")) {
            outputDevice.printMessageNl("Please enter a number between 1 and 13");
            runOwnerInterface(store, dbHandler);
        }
        option = Integer.parseInt(input);

        switch (option) {
            case 1 -> {
                displayStoreState(store);
                runOwnerInterface(store, dbHandler);
            }
            case 2 -> {
                groupProductsByType(store);
                runOwnerInterface(store, dbHandler);
            }
            case 3 -> {
                countProducts(store);
                runOwnerInterface(store, dbHandler);
            }
            case 4 -> {
                removeSoldOutProducts(store);
                runOwnerInterface(store, dbHandler);
            }
            case 5 -> {
                removeProduct(store);
                runOwnerInterface(store, dbHandler);
            }
            case 6 -> {
                removeProductType(store);
                runOwnerInterface(store, dbHandler);
            }
            case 7 -> {
                increaseProductQuantity(store);
                runOwnerInterface(store, dbHandler);
            }
            case 8 -> {
                decreaseProductQuantity(store);
                runOwnerInterface(store, dbHandler);
            }
            case 9 -> {
                addProduct(store);
                runOwnerInterface(store, dbHandler);
            }
            case 10 -> {
                outputDevice.printMessageNl("Orders:\n");
                dbHandler.printAllOrders();
                runOwnerInterface(store, dbHandler);
            }
            case 11 -> {
                int index = inputDevice.getOrderIndexFromConsole(dbHandler);
                dbHandler.removeOrder(index);
                runOwnerInterface(store, dbHandler);
            }
            case 12 -> {
                outputDevice.printMessageNl("Logging out...\n");
                runFromDB();
            }
            case 13 -> {
                exit();
            }
        }
    }

    public InputDevice getInputDevice() {
        return inputDevice;
    }

    public OutputDevice getOutputDevice() {
        return outputDevice;
    }

    private void runAdminInterface(OnlineStore store, DatabaseHandler dbHandler) {
        if (!store.isInitialized()) {
            outputDevice.printMessageNl("Please initialize the store using the owner's credentials");
            Owner owner = inputDevice.readOwnerFromConsole();
            outputDevice.printMessageNl("Now also enter password for the owner: ");
            String password = inputDevice.readPasswordFromConsole();
            store.initializeStore(owner, password);
            outputDevice.printMessageNl("Store initialized successfully for owner " + owner.getName());
        }
        outputDevice.printMessageNl("\tSelect an option:\n");
        outputDevice.printMessageNl("1. Update owner info\t\t 2. Update client info");
        outputDevice.printMessageNl("3. Add client\t\t\t\t 4. Remove client");
        outputDevice.printMessageNl("5. Remove owner\t\t\t\t 6. Wipe store");
        outputDevice.printMessageNl("7. Remove all clients\t\t 8. Show clients");
        outputDevice.printMessageNl("9. Show owner info\t\t\t10. Add random products");
        outputDevice.printMessageNl("11.Logout\t\t\t\t\t12. Exit");
        String input = inputDevice.nextLine();
        int option;
        if (input.isEmpty() || !input.matches("^[1-9]$|^1[0-2]$")) {
            outputDevice.printMessageNl("Please enter a number between 1 and 12");
            runOwnerInterface(store, dbHandler);
        }
        option = Integer.parseInt(input);
        switch (option) {
            case 1 -> {
                outputDevice.printMessageNl("Enter new owner info: ");
                Owner owner = inputDevice.readOwnerFromConsole(true, dbHandler.getOwnerEmail(), dbHandler.getOwnerName(), dbHandler.getOwnerPhoneNumber());
                store.owner = owner;
                String password = inputDevice.readPasswordFromConsole(true);
                if(password.isEmpty()) password = dbHandler.getOwnerPassword();
                store.owner = owner;
                dbHandler.updateOwner(owner, password);
                runAdminInterface(store, dbHandler);
            }
            case 2 -> {
                outputDevice.printMessageNl("Enter the email address of the client to update: ");
                String email = inputDevice.readEmailFromConsole();
                while (!dbHandler.checkClientEmail(email)) {
                    outputDevice.printMessageNl("Email does not exist");
                    email = inputDevice.readEmailFromConsole(true);
                }
                outputDevice.printMessageNl("Enter new client info: ");
                String name = inputDevice.readNameFromConsole(true);
                String phoneNumber = inputDevice.readPhoneNumberFromConsole(true);
                String password = inputDevice.readPasswordFromConsole(true);
                if(password.isEmpty()) password = dbHandler.getClientPassword(email);
                if(name.isEmpty()) name = dbHandler.getClientName(email);
                if(phoneNumber.isEmpty()) phoneNumber = dbHandler.getClientPhoneNumber(email);
                Client client = new Client(name, phoneNumber, email);
                dbHandler.updateClient(client, password);
                runAdminInterface(store, dbHandler);
            }
            case 3 -> {
                outputDevice.printMessageNl("Enter new client info: ");
                Client client = inputDevice.readClientFromConsole();
                String password = inputDevice.readPasswordFromConsole();
                dbHandler.insertClient(client, password);
                runAdminInterface(store, dbHandler);
            }
            case 4 -> {
                outputDevice.printMessageNl("Enter the email address of the client to remove: ");
                String email = inputDevice.readEmailFromConsole();
                while (!dbHandler.checkClientEmail(email)) {
                    outputDevice.printMessageNl("Email does not exist");
                    email = inputDevice.readEmailFromConsole();
                }
                dbHandler.deleteClient(email);
                runAdminInterface(store, dbHandler);
            }
            case 5 -> {
                outputDevice.printMessageNl("Are you sure you want to remove the owner? (y/n)");
                String confirmation = inputDevice.nextLine();
                while (!confirmation.equals("y") && !confirmation.equals("n") && !confirmation.equals("N") && !confirmation.equals("Y")) {
                    outputDevice.printMessageNl("Please enter y or n");
                    confirmation = inputDevice.nextLine();
                }
                if (confirmation.equals("y") || confirmation.equals("Y")) {
                    dbHandler.deleteOwner();
                    runAdminInterface(store, dbHandler);
                } else {
                    runAdminInterface(store, dbHandler);
                }
            }
            case 6 -> {
                outputDevice.printMessageNl("Are you sure you want to wipe the store? (y/n)");
                String confirmation = inputDevice.nextLine();
                while (!confirmation.equals("y") && !confirmation.equals("n") && !confirmation.equals("N") && !confirmation.equals("Y")) {
                    outputDevice.printMessageNl("Please enter y or n");
                    confirmation = inputDevice.nextLine();
                }
                if (confirmation.equals("y") || confirmation.equals("Y")) {
                    dbHandler.wipeStore();
                    store.setInitialized(false);
                    runAdminInterface(store, dbHandler);
                } else {
                    runAdminInterface(store, dbHandler);
                }
            }
            case 7 -> {
                outputDevice.printMessageNl("Are you sure you want to remove all clients? (y/n)");
                String confirmation = inputDevice.nextLine();
                while (!confirmation.equals("y") && !confirmation.equals("n") && !confirmation.equals("N") && !confirmation.equals("Y")) {
                    outputDevice.printMessageNl("Please enter y or n");
                    confirmation = inputDevice.nextLine();
                }
                if (confirmation.equals("y") || confirmation.equals("Y")) {
                    dbHandler.deleteAllClients();
                    runAdminInterface(store, dbHandler);
                } else {
                    runAdminInterface(store, dbHandler);
                }
            }
            case 8 -> {
                outputDevice.printMessageNl("Clients:\n");
                dbHandler.printAllClients();
                runAdminInterface(store, dbHandler);
            }
            case 9 -> {
                outputDevice.printMessageNl("Owner info:\n");
                dbHandler.printAllOwners();
                runAdminInterface(store, dbHandler);
            }
            case 10 -> {
                outputDevice.printMessageNl("Adding random products:\n");
                inputDevice.populateStore(store);
                outputDevice.printMessageNl("Products added successfully\n");
                runAdminInterface(store, dbHandler);
            }
            case 11 -> {
                outputDevice.printMessageNl("Logging out...\n");
                runFromDB();
            }
            case 12 -> {
                exit();
            }
        }
    }

    private void runClientInterface(OnlineStore store, String email, DatabaseHandler dbHandler){
        Client client = dbHandler.retrieveClientDetails(email);
        outputDevice.printMessageNl("\tSelect an option:\n");
        outputDevice.printMessageNl("1. Display store state\t\t2. Group products by type");
        outputDevice.printMessageNl("3. Add order\t\t\t\t4. View my orders\t\t");
        outputDevice.printMessageNl("5. View total to pay\t\t6. Logout");
        outputDevice.printMessageNl("7. Exit");
        String input = inputDevice.nextLine();
        int option;
        while (input.isEmpty() || !input.matches("^[1-7]$")) {
            outputDevice.printMessageNl("Please enter a number between 1 and 7");
            input = inputDevice.nextLine();
        }
        option = Integer.parseInt(input);
        switch (option) {
            case 1 -> {
                displayStoreState(store);
                runClientInterface(store, email, dbHandler);
            }
            case 2 -> {
                groupProductsByType(store);
                runClientInterface(store, email, dbHandler);
            }
            case 3 -> {
                int index = inputDevice.getProductIndexFromConsole(store);
                int quantity = inputDevice.getQuantityFromConsole();
                while(!store.placeOrderIdx(index, client, quantity)){
                    quantity = inputDevice.getQuantityFromConsole();
                }
                int id = dbHandler.getOrderID(client, store.getProductList()[index], quantity);
                outputDevice.printMessageNl("Order " + id + " costing " + dbHandler.getOrderValue(id) + " successfully placed");
                runClientInterface(store, email, dbHandler);
            }
            case 4 -> {
                outputDevice.printMessageNl("Your orders:\n");
                dbHandler.printClientOrders(email);
                runClientInterface(store, email, dbHandler);
            }
            case 5 -> {
                outputDevice.printMessageNl("Total to pay: " + dbHandler.getClientToPay(email));
                runClientInterface(store, email, dbHandler);
            }
            case 6 -> {
                outputDevice.printMessageNl("Logging out...\n");
                runFromDB();
            }
            case 7 -> {
                exit();
            }
        }
    }

    private void runFromDB(){
        DatabaseInitializer.initializeDatabase();
        outputDevice.printMessageNl("Starting store from database");
        String url = DatabaseInitializer.getURL();
        DatabaseHandler dbHandler = new DatabaseHandler(url);
        OnlineStore store = dbHandler.retrieveOnlineStoreFromDB();
        store.setDatabaseHandler(dbHandler);
        ArrayList<String> authInfo = inputDevice.getAuthenticationInfoFromConsole();
        String email = authInfo.get(0);
        String password = authInfo.get(1);
        String role = dbHandler.checkRole(email, password);
        switch (role){
            case "owner" -> {
                if(!store.isInitialized()){
                    outputDevice.printMessageNl("Store not initialized\nPlease contact the admin\nExiting...");
                    System.exit(1);
                }
                outputDevice.printMessageNl("Logged in as owner\n\n");
                runOwnerInterface(store, dbHandler);
            }
            case "client" -> {
                if(!store.isInitialized()){
                    outputDevice.printMessageNl("Store not initialized\nPlease contact the admin\nExiting...");
                    System.exit(1);
                }
                outputDevice.printMessageNl("Logged in as client\n\n");
                runClientInterface(store, email, dbHandler);
            }
            case "admin" -> {
                outputDevice.printMessageNl("Logged in as admin\n\n");
                runAdminInterface(store, dbHandler);
            }
            case "wrongPassword" -> {
                outputDevice.printMessageNl("Wrong password\n");
                runFromDB();
            }
            case "notFound" -> {
                outputDevice.printMessageNl("Registering new client with email " + email);
                String name = inputDevice.readNameFromConsole();
                String phoneNumber = inputDevice.readPhoneNumberFromConsole();
                Client client = new Client(name, phoneNumber, email);
                dbHandler.insertClient(client, password);
                outputDevice.printMessageNl("Client registered successfully\n");
                if(!store.isInitialized()){
                    outputDevice.printMessageNl("Store not initialized\nPlease contact the admin\nExiting...");
                    System.exit(1);
                }
                runClientInterface(store, email, dbHandler);
            }
            default -> {
                outputDevice.printMessageNl("Invalid credentials");
                System.exit(1);
            }
        }
    }
    public void run(String[] args) {
        outputDevice.setFileOutputStream("output.txt");
        if(args.length != 1){
            outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
            System.exit(1);
        }
        else switch (args[0]) {
            case "scratch" -> runFromScratch();

            case "old" -> {
                outputDevice.printMessageNl("Starting store from file\nProvide file name:\n");
                String fileName = inputDevice.nextLine();
                Path filePath = Paths.get(fileName);
                if (Files.exists(filePath) && Files.isReadable(filePath)) {
                    inputDevice.setFileInputStream(fileName);
                    runFromFile();
                } else {
                    outputDevice.printMessageNl("File does not exist or cannot be read. Please try again.");
                    System.exit(1);
                }
            }

            case "db" -> runFromDB();

            default -> {
                outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
                System.exit(1);
            }
        }

    }
}

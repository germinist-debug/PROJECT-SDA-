import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    
    private final InputPort input;
    private final OutputPort output;
    private final FileService fileService;
    private final StoreController storeController;
    private final AuthController authController;
    private final InputPort fileInput; // For old mode
    
    public Application(InputPort input, OutputPort output,
                       FileService fileService,
                       StoreController storeController,
                       AuthController authController,
                       InputPort fileInput) {
        this.input = input;
        this.output = output;
        this.fileService = fileService;
        this.storeController = storeController;
        this.authController = authController;
        this.fileInput = fileInput;
    }
    
    public OutputPort getOutput() {
        return output;
    }
    
    private void runFromScratch() {
        output.println("\nDo you want to generate a dummy owner? (y/n)");
        String response = input.readLine();
        while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
            output.println("Please enter y or n");
            response = input.readLine();
        }
        
        Owner owner;
        if (response.equalsIgnoreCase("y")) {
            owner = new Owner("John", "1234567890", "abc@gmail.com");
        } else {
            owner = input.readOwner(false, "", "", "");
        }
        
        OnlineStore store = storeController.getStore();
        store.setOwner(owner);
        
        output.println("\nDo you want to generate random products? (y/n)");
        response = input.readLine();
        while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
            output.println("Please enter y or n");
            response = input.readLine();
        }
        
        if (response.equalsIgnoreCase("y")) {
            output.printStore(store);
            output.println("\nAdding products:");
            java.util.ArrayList<Product> products = input.getRandomProducts();
            for (Product product : products) {
                output.println(product);
            }
            store.setProductList(products);
        }
        
        fileService.saveStoreToFile(store);
        runFileCommandInterface(store);
    }
    
    private void runFromFile() {
        Owner owner = fileService.loadOwnerFromFile();
        java.util.ArrayList<Product> products = fileService.loadProductsFromFile();
        OnlineStore store = storeController.getStore();
        store.setOwner(owner);
        store.setProductList(products);
        output.printStore(store);
        runFileCommandInterface(store);
    }
    
    private void runFileCommandInterface(OnlineStore store) {
        output.println("\tSelect an option:\n");
        output.println("1. Display store state\t\t\t2. Group products by type");
        output.println("3. Count products\t\t\t\t4. Remove sold out products");
        output.println("5. Remove product\t\t\t\t6. Remove product type");
        output.println("7. Increase product quantity\t8. Decrease product quantity");
        output.println("9. Add product\t\t\t\t\t10. Exit");
        
        String choice = input.readLine();
        if (choice.isEmpty() || !choice.matches("^[1-9]$|^10$")) {
            output.println("Please enter a number between 1 and 10");
            runFileCommandInterface(store);
            return;
        }
        
        int option = Integer.parseInt(choice);
        switch (option) {
            case 1 -> storeController.displayStoreState();
            case 2 -> storeController.groupProductsByType();
            case 3 -> storeController.countProducts();
            case 4 -> storeController.removeSoldOutProducts();
            case 5 -> {
                int index = input.readProductIndex(store);
                storeController.removeProduct(index);
            }
            case 6 -> {
                output.println("Enter product type: ");
                String productType = input.readLine().toUpperCase();
                storeController.removeProductType(productType);
            }
            case 7 -> {
                int index = input.readProductIndex(store);
                int quantity = input.readQuantity();
                storeController.increaseProductQuantity(index, quantity);
            }
            case 8 -> {
                int index = input.readProductIndex(store);
                int quantity = input.readQuantity();
                storeController.decreaseProductQuantity(index, quantity);
            }
            case 9 -> {
                java.util.ArrayList<String> nameAndType = input.readNameAndType();
                String name = nameAndType.get(0);
                String type = nameAndType.get(1);
                String size = input.readSize();
                String color = input.readColor();
                int price = input.readPrice();
                int quantity = input.readQuantity();
                
                Product product;
                if (type.equals("bottomWear")) {
                    product = new BottomWear(name, size, color, quantity, price);
                } else {
                    product = new TopWear(name, size, color, quantity, price);
                }
                storeController.addProduct(product);
            }
            case 10 -> {
                output.println("Exiting...");
                System.exit(0);
            }
        }
        
        fileService.saveStoreToFile(store);
        runFileCommandInterface(store);
    }
    
    public void run(String[] args) {
        if (output instanceof ConsoleOutput) {
            ((ConsoleOutput) output).setFileOutputStream("output.txt");
        }
        
        if (args.length != 1) {
            output.println("Usage: java Application <scratch|old|db>");
            System.exit(1);
        }
        
        switch (args[0]) {
            case "scratch" -> runFromScratch();
            case "old" -> {
                output.println("Starting store from file\nProvide file name:\n");
                String fileName = input.readLine();
                Path filePath = Paths.get(fileName);
                if (Files.exists(filePath) && Files.isReadable(filePath)) {
                    fileService.setFileInputStream(fileName);
                    runFromFile();
                } else {
                    output.println("File does not exist or cannot be read.");
                    System.exit(1);
                }
            }
            case "db" -> authController.runFromDB();
            default -> {
                output.println("Usage: java Application <scratch|old|db>");
                System.exit(1);
            }
        }
    }
}
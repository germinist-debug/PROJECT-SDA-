import java.util.ArrayList;

public class StoreInitializer {
    
    private ConsoleInputReader input;
    private ConsoleOutputWriter output;
    private RandomProductGenerator randomGenerator;
    
    public StoreInitializer(ConsoleInputReader input, ConsoleOutputWriter output) {
        this.input = input;
        this.output = output;
        this.randomGenerator = new RandomProductGenerator();
    }
    
    public OnlineStore createStoreFromScratch() {
        output.printLine("\nDo you want to generate a dummy owner? (y/n)");
        String answer = input.readLine();
        
        Owner owner;
        if(answer.equalsIgnoreCase("y")) {
            owner = new Owner("John Doe", "1234567890", "john@example.com");
        } else {
            owner = createOwnerFromInput();
        }
        
        OnlineStore store = new OnlineStore(owner);
        
        output.printLine("\nDo you want to generate random products? (y/n)");
        answer = input.readLine();
        
        if(answer.equalsIgnoreCase("y")) {
            ArrayList<Product> products = randomGenerator.generateRandomProducts();
            for(Product product : products) {
                store.addProduct(product);
            }
            output.printLine("Added " + products.size() + " random products");
        }
        
        return store;
    }
    
    private Owner createOwnerFromInput() {
        String name = input.readName();
        String phone = input.readPhoneNumber();
        String email = input.readEmail();
        return new Owner(name, phone, email);
    }
    
    public OnlineStore createEmptyStore() {
        output.printLine("Enter owner details:");
        Owner owner = createOwnerFromInput();
        return new OnlineStore(owner);
    }
}
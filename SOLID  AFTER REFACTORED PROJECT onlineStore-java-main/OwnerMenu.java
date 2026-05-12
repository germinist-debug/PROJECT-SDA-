public class OwnerMenu {
    
    private final InputPort input;
    private final OutputPort output;
    private final StoreController storeController;
    private final OrderRepository orderRepo;
    private final AuthController authController;
    
    public OwnerMenu(InputPort input, OutputPort output, 
                     StoreController storeController,
                     OrderRepository orderRepo,
                     AuthController authController) {
        this.input = input;
        this.output = output;
        this.storeController = storeController;
        this.orderRepo = orderRepo;
        this.authController = authController;
    }
    
    public void show(OnlineStore store) {
        output.println("\tSelect an option:\n");
        output.println("1. Display store state\t\t\t2. Group products by type");
        output.println("3. Count products\t\t\t\t4. Remove sold out products");
        output.println("5. Remove product\t\t\t\t6. Remove product type");
        output.println("7. Increase product quantity\t8. Decrease product quantity");
        output.println("9. Add product\t\t\t\t\t10. View orders");
        output.println("11. Fulfill order\t\t\t\t12. Logout\n13. Exit");
        
        String choice = input.readLine();
        if (choice.isEmpty() || !choice.matches("^[1-9]$|^1[0-3]$")) {
            output.println("Please enter a number between 1 and 13");
            show(store);
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
                ArrayList<String> nameAndType = input.readNameAndType();
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
                output.println("Orders:\n");
                orderRepo.printAll();
            }
            case 11 -> {
                output.println("Enter order ID: ");
                try {
                    int id = Integer.parseInt(input.readLine());
                    orderRepo.delete(id);
                    output.println("Order " + id + " fulfilled");
                } catch (NumberFormatException e) {
                    output.println("Invalid ID");
                }
            }
            case 12 -> {
                output.println("Logging out...\n");
                authController.runFromDB();
                return;
            }
            case 13 -> {
                output.println("Exiting...");
                System.exit(0);
            }
        }
        
        // Save after each operation
        if (output instanceof ConsoleOutput) {
            ((ConsoleOutput) output).saveToFile(store);
        }
        
        show(store);
    }
}
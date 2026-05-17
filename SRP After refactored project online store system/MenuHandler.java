public class MenuHandler {
    
    private ConsoleInputReader input;
    private ConsoleOutputWriter output;
    private StoreDisplayService displayService;
    private ProductService productService;
    private OnlineStore store;
    
    public MenuHandler(ConsoleInputReader input, ConsoleOutputWriter output, 
                       StoreDisplayService displayService, ProductService productService,
                       OnlineStore store) {
        this.input = input;
        this.output = output;
        this.displayService = displayService;
        this.productService = productService;
        this.store = store;
    }
    
    public void runOwnerMenu() {
        boolean running = true;
        
        while(running) {
            output.printLine("\n========== OWNER MENU ==========");
            output.printLine("1. Display store state");
            output.printLine("2. Group products by type");
            output.printLine("3. Count products");
            output.printLine("4. Remove sold out products");
            output.printLine("5. Remove a product");
            output.printLine("6. Remove all products of a type");
            output.printLine("7. Increase product quantity");
            output.printLine("8. Decrease product quantity");
            output.printLine("9. Add new product");
            output.printLine("10. Exit");
            output.printLine("=================================");
            output.print("Choose option: ");
            
            int choice = input.readInt();
            
            switch(choice) {
                case 1:
                    displayService.displayStoreState(store);
                    break;
                case 2:
                    displayService.displayGroupedProducts(store);
                    break;
                case 3:
                    displayService.displayProductCount(store);
                    break;
                case 4:
                    productService.removeSoldOutProducts();
                    break;
                case 5:
                    productService.removeProduct();
                    break;
                case 6:
                    productService.removeProductType();
                    break;
                case 7:
                    productService.increaseProductQuantity();
                    break;
                case 8:
                    productService.decreaseProductQuantity();
                    break;
                case 9:
                    productService.addProduct();
                    break;
                case 10:
                    output.printLine("Goodbye!");
                    running = false;
                    break;
                default:
                    output.printLine("Invalid option. Try again.");
            }
        }
    }
}
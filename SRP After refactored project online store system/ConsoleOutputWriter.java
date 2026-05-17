public class ConsoleOutputWriter {
    
    public void print(Object message) {
        System.out.print(message);
    }
    
    public void printLine(Object message) {
        System.out.println(message);
    }
    
    public void printStore(OnlineStore store) {
        printLine(store.getOwner());
        if(store.getProductList().length == 0) {
            printLine("Store is empty");
            return;
        }
        int i = 1;
        for(Product product : store.getProductList()) {
            printLine(i + ". " + product.toString());
            i++;
        }
    }
    
    public void printMenu(String[] options) {
        printLine("\nSelect an option:");
        for(int i = 0; i < options.length; i++) {
            printLine((i+1) + ". " + options[i]);
        }
    }
}
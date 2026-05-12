import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConsoleOutput implements OutputPort {
    
    private FileOutputStream fileOutputStream;
    
    public ConsoleOutput() {
        // console output uses System.out by default
    }
    
    // ========== CONSOLE OUTPUT METHODS ==========
    
    @Override
    public void print(Object message) {
        System.out.print(message);
    }
    
    @Override
    public void println(Object message) {
        System.out.println(message);
    }
    
    @Override
    public void printStore(OnlineStore store) {
        println(store.getOwner());
        
        if (store.getProductList().length == 0) {
            println("Store is empty");
            return;
        }
        
        int i = 1;
        for (Product product : store.getProductList()) {
            println(i + ". " + product.toString());
            i++;
        }
    }
    
    // ========== FILE OUTPUT METHODS ==========
    
    @Override
    public void setFileOutputStream(String fileName) {
        try {
            this.fileOutputStream = new FileOutputStream(fileName, true);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (SecurityException e) {
            System.out.println("Permission denied for file: " + fileName);
        }
    }
    
    @Override
    public void closeFileStream() {
        if (fileOutputStream == null) return;
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }
    
    @Override
    public void saveToFile(OnlineStore store) {
        if (fileOutputStream == null) {
            System.out.println("File output stream not set. Call setFileOutputStream first.");
            return;
        }
        
        writeToFile(store.getOwner());
        
        if (store.getProductList().length == 0) {
            writeToFile("Store is empty");
            return;
        }
        
        int i = 1;
        for (Product product : store.getProductList()) {
            writeToFile(i + ". " + product.toString());
            i++;
        }
    }
    
    // ========== PRIVATE HELPER ==========
    
    private void writeToFile(Object message) {
        try {
            fileOutputStream.write(message.toString().getBytes());
            fileOutputStream.write("\n".getBytes());
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}
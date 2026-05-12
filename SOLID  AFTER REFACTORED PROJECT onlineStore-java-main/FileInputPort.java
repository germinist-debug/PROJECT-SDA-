import java.util.ArrayList;

public interface FileInputPort {
    
    // File stream management
    void setFileInputStream(String fileName);
    void closeFileInputStream();
    boolean hasFileScanner();
    
    // Read from file
    Owner readOwnerFromFile();
    ArrayList<Product> readProductsFromFile();
}
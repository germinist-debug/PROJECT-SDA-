import java.util.ArrayList;

public interface InputPort {
    
    // Basic input
    String readLine();
    String readPassword();
    String readEmail();
    String readName();
    String readPhoneNumber();
    
    // Product related
    int readProductIndex(OnlineStore store);
    int readQuantity();
    int readPrice();
    String readSize();
    String readColor();
    ArrayList<String> readNameAndType();
    
    // Authentication
    ArrayList<String> getAuthenticationInfo();
    
    // Person input
    Owner readOwner(boolean update, String oldEmail, String oldName, String oldPhone);
    Client readClient(boolean update, String oldName, String oldPhone, String oldEmail);
    
    // Store operations
    void populateStore(OnlineStore store);
    ArrayList<Product> getRandomProducts();
}
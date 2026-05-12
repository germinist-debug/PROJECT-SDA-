import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConsoleInput implements InputPort, FileInputPort {
    
    private final Scanner scanner;
    private FileInputStream fileInputStream;
    private Scanner fileScanner;
    
    public ConsoleInput() {
        this.scanner = new Scanner(System.in);
        this.fileScanner = null;
    }
    
    // ========== FILE INPUT PORT METHODS ==========
    
    @Override
    public void setFileInputStream(String fileName) {
        try {
            this.fileInputStream = new FileInputStream(fileName);
            this.fileScanner = new Scanner(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (SecurityException e) {
            System.out.println("Permission denied for file: " + fileName);
        }
    }
    
    @Override
    public void closeFileInputStream() {
        if (fileInputStream == null) return;
        try {
            fileInputStream.close();
            if (fileScanner != null) {
                fileScanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }
    
    @Override
    public boolean hasFileScanner() {
        return fileScanner != null;
    }
    
    @Override
    public Owner readOwnerFromFile() {
        if (fileScanner == null || !fileScanner.hasNextLine()) return null;
        
        String[] ownerInfo = new String[3];
        String[] line;
        for (int i = 0; i < 3; i++) {
            if (!fileScanner.hasNextLine()) return null;
            line = fileScanner.nextLine().split(" ");
            ownerInfo[i] = line[line.length - 1];
        }
        
        Owner owner = new Owner(ownerInfo[0], ownerInfo[1], ownerInfo[2]);
        try {
            owner.checkAttributes();
        } catch (InvalidPersonAttribute e) {
            System.out.println("Invalid owner data in file: " + e.getMessage());
        }
        return owner;
    }
    
    @Override
    public ArrayList<Product> readProductsFromFile() {
        ArrayList<Product> products = new ArrayList<>();
        if (fileScanner == null) return products;
        
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.equals("Store is empty")) return products;
            if (!fileScanner.hasNextLine()) break;
            
            String productName = fileScanner.nextLine().split(" ")[1].toUpperCase();
            productName = productName.substring(0, productName.length() - 1);
            
            String size = fileScanner.nextLine().split(" ")[2].toUpperCase();
            String color = fileScanner.nextLine().split(" ")[2].toUpperCase();
            int quantity = Integer.parseInt(fileScanner.nextLine().split(" ")[2]);
            int price = Integer.parseInt(fileScanner.nextLine().split(" ")[2]);
            
            boolean found = false;
            for (BottomWear.BottomWearType type : BottomWear.BottomWearType.values()) {
                if (type.toString().equals(productName)) {
                    products.add(new BottomWear(productName, size, color, quantity, price));
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                for (TopWear.TopWearType type : TopWear.TopWearType.values()) {
                    if (type.toString().equals(productName)) {
                        products.add(new TopWear(productName, size, color, quantity, price));
                        break;
                    }
                }
            }
        }
        return products;
    }
    
    // ========== PRIVATE HELPER METHODS (same as before) ==========
    
    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+") || email.isEmpty();
    }
    
    private boolean isValidPassword(String password) {
        return password.length() >= 3;
    }
    
    private boolean isValidName(String name) {
        return name.length() >= 3;
    }
    
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("[0-9]+") && phone.length() == 10;
    }
    
    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    
    private BottomWear createRandomBottomWear() {
        Product.sizes[] sizes = Product.sizes.values();
        Product.colors[] colors = Product.colors.values();
        BottomWear.BottomWearType[] types = BottomWear.BottomWearType.values();
        
        return new BottomWear(
            types[randomInt(0, types.length)].toString(),
            sizes[randomInt(0, sizes.length)].toString(),
            colors[randomInt(0, colors.length)].toString(),
            randomInt(0, 101),
            randomInt(0, 501)
        );
    }
    
    private TopWear createRandomTopWear() {
        Product.sizes[] sizes = Product.sizes.values();
        Product.colors[] colors = Product.colors.values();
        TopWear.TopWearType[] types = TopWear.TopWearType.values();
        
        return new TopWear(
            types[randomInt(0, types.length)].toString(),
            sizes[randomInt(0, sizes.length)].toString(),
            colors[randomInt(0, colors.length)].toString(),
            randomInt(0, 101),
            randomInt(0, 501)
        );
    }
    
    // ========== INPUT PORT INTERFACE METHODS ==========
    
    @Override
    public String readLine() {
        return scanner.nextLine();
    }
    
    @Override
    public String readPassword() {
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        
        while (!isValidPassword(password)) {
            System.out.println("Invalid password (min 3 characters). Enter password: ");
            password = scanner.nextLine();
        }
        return password;
    }
    
    @Override
    public String readEmail() {
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        
        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Enter email: ");
            email = scanner.nextLine();
        }
        return email;
    }
    
    @Override
    public String readName() {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        
        while (!isValidName(name)) {
            System.out.println("Invalid name (min 3 characters). Enter name: ");
            name = scanner.nextLine();
        }
        return name;
    }
    
    @Override
    public String readPhoneNumber() {
        System.out.println("Enter phone number: ");
        String phone = scanner.nextLine();
        
        while (!isValidPhoneNumber(phone)) {
            System.out.println("Invalid phone number (10 digits only). Enter phone number: ");
            phone = scanner.nextLine();
        }
        return phone;
    }
    
    @Override
    public int readProductIndex(OnlineStore store) {
        int index = -1;
        int maxIndex = store.getProductList().length;
        
        while (index < 0 || index >= maxIndex) {
            System.out.println("Enter product index (1 to " + maxIndex + "): ");
            String input = scanner.nextLine();
            
            if (!input.matches("[1-9][0-9]*")) {
                System.out.println("Invalid index. Please enter a number.");
                continue;
            }
            
            index = Integer.parseInt(input) - 1;
            
            if (index < 0 || index >= maxIndex) {
                System.out.println("Index out of range. Please enter between 1 and " + maxIndex);
            }
        }
        return index;
    }
    
    @Override
    public int readQuantity() {
        int quantity = -1;
        
        while (quantity < 0) {
            System.out.println("Enter quantity: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return quantity;
    }
    
    @Override
    public int readPrice() {
        int price = -1;
        
        while (price < 0) {
            System.out.println("Enter price: ");
            try {
                price = Integer.parseInt(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Price cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return price;
    }
    
    @Override
    public String readSize() {
        System.out.println("Enter size (XS, S, M, L, XL, XXL): ");
        String size = scanner.nextLine().toUpperCase();
        
        while (!Product.checkSize(size)) {
            System.out.println("Invalid size. Valid sizes: XS, S, M, L, XL, XXL");
            size = scanner.nextLine().toUpperCase();
        }
        return size;
    }
    
    @Override
    public String readColor() {
        System.out.println("Enter color: ");
        String color = scanner.nextLine().toUpperCase();
        
        while (!Product.checkColor(color)) {
            System.out.println("Invalid color. Please enter a valid color.");
            color = scanner.nextLine().toUpperCase();
        }
        return color;
    }
    
    @Override
    public ArrayList<String> readNameAndType() {
        String name = null;
        boolean valid = false;
        
        while (!valid) {
            System.out.println("Enter product name: ");
            name = scanner.nextLine().toUpperCase();
            
            try {
                Product.checkType(name);
                valid = true;
            } catch (InvalidProductTypeException e) {
                System.out.println("Invalid product type: " + name + ". Please try again.");
            }
        }
        
        ArrayList<String> result = new ArrayList<>();
        result.add(name);
        result.add(name);
        return result;
    }
    
    @Override
    public ArrayList<String> getAuthenticationInfo() {
        ArrayList<String> authInfo = new ArrayList<>();
        System.out.println("=== LOGIN / REGISTER ===");
        authInfo.add(readEmail());
        authInfo.add(readPassword());
        return authInfo;
    }
    
    @Override
    public Owner readOwner(boolean update, String oldEmail, String oldName, String oldPhone) {
        if (update) {
            System.out.println("Enter new owner info (leave empty to keep current):");
        }
        
        String name = readName();
        String phone = readPhoneNumber();
        String email = readEmail();
        
        if (update) {
            if (name.isEmpty()) name = oldName;
            if (phone.isEmpty()) phone = oldPhone;
            if (email.isEmpty()) email = oldEmail;
        }
        
        Owner owner = new Owner(name, phone, email);
        
        try {
            owner.checkAttributes();
        } catch (InvalidPersonAttribute e) {
            System.out.println("Invalid owner data: " + e.getMessage());
            return readOwner(update, oldEmail, oldName, oldPhone);
        }
        
        return owner;
    }
    
    @Override
    public Client readClient(boolean update, String oldName, String oldPhone, String oldEmail) {
        if (update) {
            System.out.println("Enter new client info (leave empty to keep current):");
        }
        
        String name = readName();
        String phone = readPhoneNumber();
        String email = readEmail();
        
        if (update) {
            if (name.isEmpty()) name = oldName;
            if (phone.isEmpty()) phone = oldPhone;
            if (email.isEmpty()) email = oldEmail;
        }
        
        Client client = new Client(name, phone, email);
        
        try {
            client.checkAttributes();
        } catch (InvalidPersonAttribute e) {
            System.out.println("Invalid client data: " + e.getMessage());
            return readClient(update, oldName, oldPhone, oldEmail);
        }
        
        return client;
    }
    
    @Override
    public void populateStore(OnlineStore store) {
        ArrayList<Product> products = getRandomProducts();
        for (Product product : products) {
            store.addProduct(product);
        }
        System.out.println("Added " + products.size() + " random products to store.");
    }
    
    @Override
    public ArrayList<Product> getRandomProducts() {
        ArrayList<Product> products = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            products.add(createRandomBottomWear());
        }
        
        for (int i = 0; i < 5; i++) {
            products.add(createRandomTopWear());
        }
        
        return products;
    }
}
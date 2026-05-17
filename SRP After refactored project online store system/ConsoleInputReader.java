import java.util.Scanner;

public class ConsoleInputReader {
    
    private Scanner scanner;
    
    public ConsoleInputReader() {
        this.scanner = new Scanner(System.in);
    }
    
    public String readLine() {
        return scanner.nextLine();
    }
    
    public int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }
    
    public String readEmail() {
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        while(!email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+") && !email.equals("admin")) {
            System.out.println("Invalid email. Enter again: ");
            email = scanner.nextLine();
        }
        return email;
    }
    
    public String readPassword() {
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        while(password.length() < 3 && !password.equals("admin")) {
            System.out.println("Password too short (min 3 chars). Enter again: ");
            password = scanner.nextLine();
        }
        return password;
    }
    
    public String readName() {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        while(name.length() < 3) {
            System.out.println("Name too short (min 3 chars). Enter again: ");
            name = scanner.nextLine();
        }
        return name;
    }
    
    public String readPhoneNumber() {
        System.out.println("Enter phone number: ");
        String phone = scanner.nextLine();
        while(!phone.matches("[0-9]+") || phone.length() != 10) {
            System.out.println("Invalid phone (10 digits). Enter again: ");
            phone = scanner.nextLine();
        }
        return phone;
    }
    
    public String readSize() {
        System.out.println("Enter size (XS,S,M,L,XL,XXL): ");
        String size = scanner.nextLine().toUpperCase();
        while(!Product.checkSize(size)) {
            System.out.println("Invalid size. Enter again: ");
            size = scanner.nextLine().toUpperCase();
        }
        return size;
    }
    
    public String readColor() {
        System.out.println("Enter color: ");
        String color = scanner.nextLine().toUpperCase();
        while(!Product.checkColor(color)) {
            System.out.println("Invalid color. Enter again: ");
            color = scanner.nextLine().toUpperCase();
        }
        return color;
    }
    
    public int readPrice() {
        int price = -1;
        while(price < 0) {
            System.out.println("Enter price: ");
            try {
                price = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Enter a valid number");
            }
        }
        return price;
    }
    
    public int readQuantity() {
        int quantity = -1;
        while(quantity < 0) {
            System.out.println("Enter quantity: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Enter a valid number");
            }
        }
        return quantity;
    }
}
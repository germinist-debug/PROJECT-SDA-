import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInputReader {
    
    private Scanner fileScanner;
    private boolean isOpen = false;
    
    public void openFile(String fileName) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        this.fileScanner = new Scanner(fis);
        this.isOpen = true;
    }
    
    public void closeFile() {
        if(fileScanner != null) {
            fileScanner.close();
            isOpen = false;
        }
    }
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public Owner readOwnerFromFile() {
        if(!isOpen) return null;
        String[] ownerInfo = new String[3];
        for(int i = 0; i < 3; i++) {
            String[] line = fileScanner.nextLine().split(" ");
            ownerInfo[i] = line[line.length - 1];
        }
        return new Owner(ownerInfo[0], ownerInfo[1], ownerInfo[2]);
    }
    
    public ArrayList<Product> readProductsFromFile() {
        ArrayList<Product> products = new ArrayList<>();
        if(!isOpen) return products;
        
        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if(line.equals("Store is empty")) return products;
            
            // Parse product from file format
            // This is simplified - adjust based on your file format
        }
        return products;
    }
}
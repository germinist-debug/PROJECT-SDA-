import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomProductGenerator {
    
    private String[] bottomWearNames = {"JEANS", "SHORTS", "PANTS", "SKIRT"};
    private String[] topWearNames = {"TSHIRT", "SHIRT", "BLOUSE", "SWEATER"};
    private String[] sizes = {"XS", "S", "M", "L", "XL"};
    private String[] colors = {"RED", "BLUE", "BLACK", "WHITE", "GREEN"};
    
    public ArrayList<Product> generateRandomProducts() {
        ArrayList<Product> products = new ArrayList<>();
        
        // Add 5 random bottom wears
        for(int i = 0; i < 5; i++) {
            products.add(generateRandomBottomWear());
        }
        
        // Add 5 random top wears
        for(int i = 0; i < 5; i++) {
            products.add(generateRandomTopWear());
        }
        
        return products;
    }
    
    private BottomWear generateRandomBottomWear() {
        String name = bottomWearNames[randomInt(0, bottomWearNames.length)];
        String size = sizes[randomInt(0, sizes.length)];
        String color = colors[randomInt(0, colors.length)];
        int quantity = randomInt(0, 101);
        int price = randomInt(10, 501);
        return new BottomWear(name, size, color, quantity, price);
    }
    
    private TopWear generateRandomTopWear() {
        String name = topWearNames[randomInt(0, topWearNames.length)];
        String size = sizes[randomInt(0, sizes.length)];
        String color = colors[randomInt(0, colors.length)];
        int quantity = randomInt(0, 101);
        int price = randomInt(10, 501);
        return new TopWear(name, size, color, quantity, price);
    }
    
    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
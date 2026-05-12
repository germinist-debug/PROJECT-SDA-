import java.util.ArrayList;

// TopWear is a concrete subclass of Product
// Follows LSP: can replace Product without breaking code

public class TopWear extends Product {

    // Specific types for TopWear
    public enum TopWearType {
        TSHIRT, SHIRT, BLOUSE, SWEATER, HOODIE, CARDIGAN, JACKET, COAT
    }

    public TopWear(String name, String size, String color, int quantity, int price) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
    }

    public TopWear(String name, String size, String color, int price) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = 0;
    }

    // Utility method
    public static ArrayList<String> getTopWearTypes() {
        ArrayList<String> topWearTypes = new ArrayList<>();
        for (TopWearType type : TopWearType.values()) {
            topWearTypes.add(type.toString());
        }
        return topWearTypes;
    }

    /*
     * =========================================================
     * 🔥 LSP IMPLEMENTATION
     * =========================================================
     *
     * ✔ TopWear behaves exactly like Product contract
     * ✔ Safe substitution: Product p = new TopWear();
     * ✔ No need for parent to know this class
     */
    @Override
    public String getType() {
        return "topWear";
    }
}

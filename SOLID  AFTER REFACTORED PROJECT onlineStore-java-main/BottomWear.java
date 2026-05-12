import java.util.ArrayList;

// BottomWear is a concrete subclass of Product
// It follows LSP: can be used wherever Product is expected

public class BottomWear extends Product {

    // Specific types for BottomWear
    public enum BottomWearType { JEANS, SHORTS, PANTS, SKIRT }

    public BottomWear(String name, String size, String color, int quantity, int price) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public BottomWear(String name, String size, String color, int price) {
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = 0;
    }

    // Utility method (not related to OCP/LSP, just helper)
    public static ArrayList<String> getBottomWearTypes() {
        ArrayList<String> bottomWearTypes = new ArrayList<>();
        for (BottomWearType type : BottomWearType.values()) {
            bottomWearTypes.add(type.toString());
        }
        return bottomWearTypes;
    }

    /*
     * =========================================================
     * 🔥 LSP IMPLEMENTATION
     * =========================================================
     *
     * ✔ Product class can be replaced by BottomWear safely
     * ✔ No instanceof check required in parent class
     * ✔ BottomWear defines its own behavior
     */
    @Override
    public String getType() {
        return "bottomWear";
    }
}
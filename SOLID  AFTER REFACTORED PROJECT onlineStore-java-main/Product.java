// Represents a product in the store with common attributes
// Applied SRP + OCP + LSP principles properly

public abstract class Product implements ProductHandling, Comparable<Product> {

    // Common attributes for all products
    protected String size, color, name;
    protected int quantity, price;

    // Enums for validation
    public enum colors {
        RED, BLUE, GREEN, YELLOW, BLACK, WHITE, ORANGE, PURPLE, PINK, BROWN,
        GREY, BEIGE, NAVY, OLIVE, MAROON, LIME, TEAL, INDIGO, VIOLET,
        AQUA, TURQUOISE, SILVER, LAVENDER, TAN, KHAKI, PLUM, GOLD,
        MAGENTA, SALMON, CRIMSON, CORAL, BURGUNDY, MUSTARD
    }

    public enum sizes { XS, S, M, L, XL, XXL }

    // Getters
    public String getSize() { return size; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public int getQuantity() { return quantity; }
    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        if (this.quantity - quantity < 0) this.quantity = 0;
        else this.quantity -= quantity;
    }

    /*
     * =========================================================
     * 🔥 LSP IMPLEMENTATION (Liskov Substitution Principle)
     * =========================================================
     *
     * ✔ Before: Product class used instanceof to decide type ❌
     * ✔ Now: Each subclass defines its own behavior ✔
     *
     * 👉 This ensures:
     * - Product can be replaced by any subclass safely
     * - No need for parent to know child types
     */

    public abstract String getType();

    /*
     * =========================================================
     * 🔥 OCP IMPLEMENTATION (Open/Closed Principle)
     * =========================================================
     *
     * ✔ Before: Product.java had instanceof checks + type logic ❌
     * ✔ Now: Type behavior moved to subclasses ✔
     *
     * 👉 Benefit:
     * - New product types can be added WITHOUT modifying this class
     * - Only extend Product and override getType()
     */


    /*
     * Comparison logic (unchanged - SRP related)
     */
    @Override
    public int compareTo(Product o) {
        if (this.name.compareTo(o.name) != 0) return this.name.compareTo(o.name);
        if (this.size.compareTo(o.size) != 0) return this.size.compareTo(o.size);
        if (this.color.compareTo(o.color) != 0) return this.color.compareTo(o.color);
        if (this.quantity != o.quantity) return this.quantity - o.quantity;
        if (this.price != o.price) return this.price - o.price;
        return 0;
    }

    /*
     * Utility methods (validation logic)
     */
    @Override
    public String toString() {
        return this.name + ":\n- size " + this.size +
               "\n- color " + this.color +
               "\n- quantity " + this.quantity +
               "\n- price " + this.price + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product product) {
            return this.name.equals(product.name)
                && this.size.equals(product.size)
                && this.color.equals(product.color);
        }
        return false;
    }

    // Validation helpers
    public static boolean checkSize(String size) {
        for (sizes s : sizes.values()) {
            if (s.toString().equals(size)) return true;
        }
        return false;
    }

    public static boolean checkColor(String color) {
        for (colors c : colors.values()) {
            if (c.toString().equals(color)) return true;
        }
        return false;
    }

    public static boolean checkPrice(int price) {
        return price >= 0;
    }

    public static boolean checkQuantity(int quantity) {
        return quantity >= 0;
    }

    public void checkAttributes() throws InvalidProductAttribute {
        if (!checkSize(size)) throw new InvalidProductSize("Invalid size");
        if (!checkColor(color)) throw new InvalidProductColor("Invalid color");
        if (!checkPrice(price)) throw new InvalidProductPrice("Invalid price");
        if (!checkQuantity(quantity)) throw new InvalidProductQuantity("Invalid quantity");
    }

    /*
     * =========================================================
     * ❌ OLD CODE (REMOVED - OCP VIOLATION)
     * =========================================================
     *
     * This method was violating OCP because:
     * - It directly depended on BottomWear & TopWear
     * - Required modification for every new product type
     *
     * public static String checkType(String name) {
     *     for(BottomWear.BottomWearType bottomWearType : BottomWear.BottomWearType.values()){
     *         if(bottomWearType.toString().equals(name)) return "bottomWear";
     *     }
     *     for(TopWear.TopWearType topWearType : TopWear.TopWearType.values()){
     *         if(topWearType.toString().equals(name)) return "topWear";
     *     }
     *     throw new InvalidProductTypeException("Invalid type");
     * }
     *
     * 👉 REASON REMOVED:
     * - Breaks OCP (modification required for new types)
     * - Breaks LSP (tight coupling with subclasses)
     */
}
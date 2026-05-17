/// Represents a product in the store with its attributes and methods

public class Product implements ProductHandling, Comparable<Product>{

    protected String size, color, name;
    protected int quantity, price;
    public enum colors{ RED, BLUE, GREEN, YELLOW, BLACK, WHITE, ORANGE, PURPLE, PINK, BROWN, GREY, BEIGE, NAVY, OLIVE, MAROON, LIME, TEAL, INDIGO, VIOLET,
                        AQUA, TURQUOISE, SILVER, LAVENDER, TAN, KHAKI, PLUM, GOLD, MAGENTA, SALMON, CRIMSON, CORAL, BURGUNDY, MUSTARD }
    public enum sizes{ XS, S, M, L, XL, XXL }

    public String getSize(){ return size; }
    public String getName(){ return name; }

    public String getType(){
        if(this instanceof BottomWear) return "bottomWear";
        else return "topWear";
    }
    public String getColor(){ return color; }
    public int getQuantity(){ return quantity; }
    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price = price; }
    public void increaseQuantity(int quantity){ this.quantity += quantity; }
    public void decreaseQuantity(int quantity){
        if(this.quantity - quantity < 0) this.quantity = 0;
        else this.quantity -= quantity;
    }

    @Override
    public int compareTo(Product o) {
        if(this.name.compareTo(o.name) != 0) return this.name.compareTo(o.name);
        if(this.size.compareTo(o.size) != 0) return this.size.compareTo(o.size);
        if(this.color.compareTo(o.color) != 0) return this.color.compareTo(o.color);
        if (this.quantity != o.quantity) return this.quantity - o.quantity;
        if (this.price != o.price) return this.price - o.price;
        return 0;
    }

    @Override
    public String toString(){
        return this.name + ":\n- size " + this.size + "\n- color " + this.color + "\n- quantity " + this.quantity + "\n" + "- price " + this.price + "\n";
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Product product){
            return this.name.equals(product.name) && this.size.equals(product.size) && this.color.equals(product.color);
        }
        return false;
    }

    public static boolean checkSize(String size){
        for(sizes s : sizes.values()){
            if(s.toString().equals(size)) return true;
        }
        return false;
    }

    public static boolean checkColor(String color){
        for(colors c : colors.values()){
            if(c.toString().equals(color)) return true;
        }
        return false;
    }

    public static boolean checkPrice(int price){
        return price >= 0;
    }

    public static boolean checkQuantity(int quantity){
        return quantity >= 0;
    }
    public void checkAttributes() throws InvalidProductAttribute{
        if(!checkSize(size)) throw new InvalidProductSize("Invalid size");
        if(!checkColor(color)) throw new InvalidProductColor("Invalid color");
        if(!checkPrice(price)) throw new InvalidProductPrice("Invalid price");
        if(!checkQuantity(quantity)) throw new InvalidProductQuantity("Invalid quantity");
    }

    public static String checkType(String name) throws InvalidProductTypeException{
            for(BottomWear.BottomWearType bottomWearType : BottomWear.BottomWearType.values()){
                if(bottomWearType.toString().equals(name)) return "bottomWear";
            }
            for(TopWear.TopWearType topWearType : TopWear.TopWearType.values()){
                if(topWearType.toString().equals(name)) return "topWear";
            }

        throw new InvalidProductTypeException("Invalid type");
    }

}

import java.util.ArrayList;

/// TopWear class that extends Product class and has a TopWearType enum
public class TopWear extends Product {

    public enum TopWearType{ TSHIRT, SHIRT, BLOUSE, SWEATER, HOODIE, CARDIGAN, JACKET, COAT }

    public TopWear(String name, String size, String color, int quantity, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
    }

    public TopWear(String name, String size, String color, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = 0;
    }

    static public ArrayList<String>getTopWearTypes(){
        ArrayList<String> topWearTypes = new ArrayList<>();
        for(TopWearType topWearType : TopWearType.values()){
            topWearTypes.add(topWearType.toString());
        }
        return topWearTypes;
    }
}

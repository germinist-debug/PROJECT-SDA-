// this class is a subclass of Product class

import java.util.ArrayList;

public class BottomWear extends Product {

    public enum BottomWearType{ JEANS, SHORTS, PANTS, SKIRT }

    public BottomWear(String name, String size, String color, int quantity, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public BottomWear(String name, String size, String color, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = 0;
    }

    static public ArrayList<String>getBottomWearTypes(){
        ArrayList<String> bottomWearTypes = new ArrayList<>();
        for(BottomWearType bottomWearType : BottomWearType.values()){
            bottomWearTypes.add(bottomWearType.toString());
        }
        return bottomWearTypes;
    }
}

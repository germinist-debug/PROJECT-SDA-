/// This class is used to throw an exception when an invalid person attribute is entered
public class InvalidProductAttribute extends Exception{
    public InvalidProductAttribute(String message){
        super(message);
    }
}

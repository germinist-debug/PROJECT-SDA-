/// This class is used to throw an exception when an invalid person attribute is entered
public class InvalidPersonAttribute extends Exception{
    public InvalidPersonAttribute(String message){
        super(message);
    }
}

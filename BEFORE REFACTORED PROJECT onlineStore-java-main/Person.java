import java.util.ArrayList;

/// Person interface is used to define a person with name, phone number and email
public interface Person {

    @Override
    public String toString();

    public ArrayList<String> getInfo();

    public void checkAttributes() throws InvalidPersonAttribute;
}
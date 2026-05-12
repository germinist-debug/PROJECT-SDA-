import java.util.ArrayList;

// this class represents the store's client
public class Client implements Person, Comparable<Client>{

    String name, phoneNumber, email;

    ArrayList<String> info = new ArrayList<>();

    public Client(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        info.add(name);
        info.add(phoneNumber);
        info.add(email);
    }

    @Override
    public int compareTo(Client o) {
        if(this.name.compareTo(o.name) != 0) return this.name.compareTo(o.name);
        if(this.phoneNumber.compareTo(o.phoneNumber) != 0) return this.phoneNumber.compareTo(o.phoneNumber);
        if(this.email.compareTo(o.email) != 0) return this.email.compareTo(o.email);
        return 0;
    }

    @Override
    public String toString(){
        return "Client name is " + this.name + "\nClient phone number is " + this.phoneNumber + "\nClient email is " + this.email + "\n";
    }

    public ArrayList<String> getInfo(){ return info; }

    private void checkName() throws InvalidPersonName{
        if(this.name.length() < 3) throw new InvalidPersonName("Invalid name");
    }

    //check phone number with regex
    private void checkPhoneNumber() throws InvalidPersonPhoneNumber{
        if(!(this.phoneNumber.matches("[0-9]+") || this.phoneNumber.isEmpty())) throw new InvalidPersonPhoneNumber("Invalid phone number");
    }

    //check email with regex
    private void checkEmail() throws InvalidPersonEmail {
        if (!(this.email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+") || this.email.isEmpty()))
            throw new InvalidPersonEmail("Invalid email");
    }

    public void checkAttributes() throws InvalidPersonAttribute{
        checkName();
        checkPhoneNumber();
        checkEmail();
    }

    public String getName(){ return name; }
    public String getPhoneNumber(){ return phoneNumber; }
    public String getEmail(){ return email; }
}

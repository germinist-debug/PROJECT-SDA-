import java.util.ArrayList;

/// Owner class is used to store information about the owner of the store

public class Owner implements Person, Comparable<Owner>{ //class A

    String name, phoneNumber, email;
    ArrayList<String> info = new ArrayList<>();

    public Owner(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        info.add(name);
        info.add(phoneNumber);
        info.add(email);
    }

    public String getName(){ return name; }
    public String getPhoneNumber(){ return phoneNumber; }
    public String getEmail(){ return email; }
    @Override
    public int compareTo(Owner o) {
        if(this.name.compareTo(o.name) != 0) return this.name.compareTo(o.name);
        if(this.phoneNumber.compareTo(o.phoneNumber) != 0) return this.phoneNumber.compareTo(o.phoneNumber);
        if(this.email.compareTo(o.email) != 0) return this.email.compareTo(o.email);
        return 0;
    }

    @Override
    public String toString(){
        return "Store owner is " + this.name + "\nOwner phone number is " + this.phoneNumber + "\nOwner email is " + this.email + "\n";
    }

    public ArrayList<String> getInfo(){ return info; }

    private void checkName() throws InvalidPersonName{
        if(this.name.length() < 3) throw new InvalidPersonName("Invalid name");
    }

    //check phone number with regex
    private void checkPhoneNumber() throws InvalidPersonPhoneNumber{
        if(!(this.phoneNumber.matches("[0-9]+") && this.phoneNumber.length() == 10))
            throw new InvalidPersonPhoneNumber("Invalid phone number");
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

}

